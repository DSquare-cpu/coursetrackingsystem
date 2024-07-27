-- PostgreSQL database schema

CREATE DATABASE course_tracking WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.UTF-8';

\connect course_tracking

CREATE TABLE public.assignments (
    assignment_id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.assignments_assignment_id_seq'),
    course_id integer NOT NULL,
    issue_date date NOT NULL,
    status character varying(20) NOT NULL CHECK ((status::text = ANY (ARRAY['marked'::character varying, 'not marked'::character varying]))),
    name character varying(50)
);

CREATE SEQUENCE public.assignments_assignment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    CACHE 1;

ALTER SEQUENCE public.assignments_assignment_id_seq OWNED BY public.assignments.assignment_id;


CREATE TABLE public.courses (
    course_id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.courses_course_id_seq'),
    course_name character varying(100) NOT NULL,
    lecturer_id integer NOT NULL,
    semester_id integer NOT NULL,
    start_date date NOT NULL
);

CREATE SEQUENCE public.courses_course_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    CACHE 1;

ALTER SEQUENCE public.courses_course_id_seq OWNED BY public.courses.course_id;


CREATE TABLE public.finals (
    final_id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.finals_final_id_seq'),
    course_id integer NOT NULL,
    final_date date NOT NULL,
    status character varying(20) NOT NULL CHECK ((status::text = ANY (ARRAY['marked'::character varying, 'not marked'::character varying]))),
    name character varying(50)
);

CREATE SEQUENCE public.finals_final_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    CACHE 1;

ALTER SEQUENCE public.finals_final_id_seq OWNED BY public.finals.final_id;


CREATE TABLE public.lecturers (
    lecturer_id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.lecturers_lecturer_id_seq'),
    name character varying(100) NOT NULL,
    email character varying(100) NOT NULL
);

CREATE SEQUENCE public.lecturers_lecturer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    CACHE 1;

ALTER SEQUENCE public.lecturers_lecturer_id_seq OWNED BY public.lecturers.lecturer_id;


CREATE TABLE public.marksheets (
    marksheet_id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.marksheets_marksheet_id_seq'),
    course_id integer NOT NULL,
    lecturer_id integer NOT NULL,
    semester_id integer NOT NULL,
    submission_status character varying(20) NOT NULL CHECK ((submission_status::text = ANY (ARRAY['submitted'::text, 'not submitted'::text, 'approved'::text]))),
    estimated_completion_time date,
    total_students integer NOT NULL,
    students_marked integer NOT NULL,
    average_score double precision NOT NULL,
    highest_score double precision NOT NULL,
    lowest_score double precision NOT NULL
);

CREATE SEQUENCE public.marksheets_marksheet_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    CACHE 1;

ALTER SEQUENCE public.marksheets_marksheet_id_seq OWNED BY public.marksheets.marksheet_id;


CREATE TABLE public.semesters (
    semester_id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.semesters_semester_id_seq'),
    semester_name character varying(50) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL
);

CREATE SEQUENCE public.semesters_semester_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    CACHE 1;

ALTER SEQUENCE public.semesters_semester_id_seq OWNED BY public.semesters.semester_id;


CREATE TABLE public.tests (
    test_id integer NOT NULL PRIMARY KEY DEFAULT nextval('public.tests_test_id_seq'),
    course_id integer NOT NULL,
    test_date date NOT NULL,
    status character varying(20) NOT NULL CHECK ((status::text = ANY (ARRAY['marked'::character varying, 'not marked'::character varying]))),
    name character varying(30)
);

CREATE SEQUENCE public.tests_test_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    CACHE 1;

ALTER SEQUENCE public.tests_test_id_seq OWNED BY public.tests.test_id;
