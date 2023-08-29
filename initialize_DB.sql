CREATE TABLE IF NOT EXISTS students (
    student_id BIGINT PRIMARY KEY, 
    student_name VARCHAR(255) NOT NULL,
    student_email VARCHAR(255) NOT NULL,
    register_date TIMESTAMP NOT NULL,
    delete_flag INT NOT NULL DEFAULT 0,
    student_icon TEXT,
    password TEXT NOT NULL,
    salt VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS admins (
    admin_id BIGINT PRIMARY KEY, 
    admin_name VARCHAR(255) NOT NULL,
    admin_email VARCHAR(255) NOT NULL,
    register_date TIMESTAMP NOT NULL,
    delete_flag INT NOT NULL DEFAULT 0,
    admin_icon TEXT,
    password TEXT NOT NULL,
    salt VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses (
    course_id BIGINT PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL,
    course_fee INT NOT NULL,
    course_image TEXT,
    register_date TIMESTAMP NOT NULL,
    start_date DATE,
    finish_date DATE,
    lesson_start_time TIME,
    lesson_duration INT,
    admin_id BIGINT NOT NULL,
    delete_flag INT NOT NULL DEFAULT 0,
    FOREIGN KEY (admin_id) references admins(admin_id)
);

CREATE TABLE IF NOT EXISTS transaction_history (
    transaction_id BIGINT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    amount INT NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    FOREIGN KEY (student_id) references students(student_id)
);

CREATE TABLE IF NOT EXISTS transaction_items (
    id BIGINT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    transaction_id BIGINT NOT NULL,
    FOREIGN KEY (course_id) references courses(course_id),
    FOREIGN KEY (transaction_id) references transaction_history(transaction_id)
);
