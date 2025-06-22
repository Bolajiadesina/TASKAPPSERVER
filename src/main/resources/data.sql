CREATE TABLE public.task (
    task_id serial4 NOT NULL,
    task_title varchar(255) NOT NULL,
    task_description varchar(255) NOT NULL,
    task_status varchar(100) NULL,
    task_created_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    task_due_date varchar(100) NOT NULL,
    CONSTRAINT task_pkey PRIMARY KEY (task_id)
);

CREATE OR REPLACE PROCEDURE public.insert_task(
    p_task_title VARCHAR(255),
    p_task_description VARCHAR(255),
    p_task_status VARCHAR(100),
    p_task_due_date VARCHAR(100),
    OUT p_return_code VARCHAR(2),
    OUT p_return_message VARCHAR(255)
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Insert the new task
    INSERT INTO public.task (
        task_title,
        task_description,
        task_status,
        task_due_date
    ) VALUES (
        p_task_title,
        p_task_description,
        p_task_status,
        p_task_due_date
    );
    
    -- Set success values
    p_return_code := '00';
    p_return_message := 'Success';
    
EXCEPTION WHEN OTHERS THEN
    -- Set error values
    p_return_code := '99';
    p_return_message := SQLERRM;
END;
$$;

CREATE OR REPLACE FUNCTION public.save_task(
    p_task_title VARCHAR(255),
    p_task_description VARCHAR(255),
    p_task_status VARCHAR(100),
    p_task_due_date VARCHAR(100)
)
RETURNS VARCHAR(2)  -- Explicit return type with length
LANGUAGE plpgsql
AS $$
DECLARE
    v_return_code VARCHAR(2);
BEGIN
    -- Validate required fields
    IF p_task_title IS NULL OR p_task_title = '' THEN
        RETURN '01';  -- Specific code for missing title
    END IF;
    
    IF p_task_description IS NULL OR p_task_description = '' THEN
        RETURN '02';  -- Specific code for missing description
    END IF;

    -- Insert the new task
    INSERT INTO public.task (
        task_title,
        task_description,
        task_status,
        task_due_date
    ) VALUES (
        p_task_title,
        p_task_description,
        p_task_status,
        p_task_due_date
    );
    
    RETURN '00';  -- Success
    
EXCEPTION WHEN OTHERS THEN
    -- Log error if needed (uncomment to use)
    -- INSERT INTO error_logs (error_message) VALUES (SQLERRM);
    
    RETURN '99';  -- General error
END;
$$;
-- Calling from another PL/pgSQL block:
DO $$
DECLARE
    v_code VARCHAR(2);
    v_msg VARCHAR(255);
BEGIN
    CALL public.insert_task(
        'Complete project',
        'Finish the Angular frontend',
        'In Progress',
        '2023-12-31',
        v_code,
        v_msg
    );
    
    RAISE NOTICE 'Return Code: %, Message: %', v_code, v_msg;
END;
$$;

CREATE OR REPLACE PROCEDURE public.get_all_tasks(
    OUT p_response_code VARCHAR(2),
    OUT p_task_cursor REFCURSOR
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Open cursor for all tasks
    OPEN p_task_cursor FOR
    SELECT 
        task_id,
        task_title,
        task_description,
        task_status,
        task_created_date,
        task_due_date
    FROM public.task
    ORDER BY task_created_date DESC;
    
    -- Set success values
    p_response_code := '00';


EXCEPTION WHEN OTHERS THEN
    -- Set error values
    p_response_code := '99';
  
    
    -- Ensure cursor is closed on error
    IF p_task_cursor IS NOT NULL THEN
        CLOSE p_task_cursor;
    END IF;
END;
$$;

CREATE OR REPLACE PROCEDURE public.get_task_by_id(
    IN p_task_id INTEGER,
    OUT p_response_code VARCHAR(2),
    OUT p_response_message VARCHAR(255),
    OUT p_task_cursor REFCURSOR
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Check if task exists
    IF NOT EXISTS (SELECT 1 FROM public.task WHERE task_id = p_task_id) THEN
        p_response_code := '01';
        p_response_message := 'Task not found';
        RETURN;
    END IF;

    -- Open cursor for the specific task
    OPEN p_task_cursor FOR
    SELECT 
        task_id,
        task_title,
        task_description,
        task_status,
        task_created_date,
        task_due_date
    FROM public.task
    WHERE task_id = p_task_id;
    
    -- Set success values
    p_response_code := '00';
    p_response_message := 'Task retrieved successfully';

EXCEPTION WHEN OTHERS THEN
    -- Set error values
    p_response_code := '99';
    p_response_message := 'Database error: ' || SQLERRM;
    
    -- Ensure cursor is closed on error
    IF p_task_cursor IS NOT NULL THEN
        CLOSE p_task_cursor;
    END IF;
END;
$$;