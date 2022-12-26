create schema db_easyr;
use db_easyr;

 -- TABLE CREATION -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

create table usr(
	id INTEGER UNSIGNED AUTO_INCREMENT,
    username VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(45) NOT NULL,  
    banned TINYINT(1) DEFAULT 0,
    daily_points INTEGER DEFAULT 0, 
    PRIMARY KEY (id)
);

create table admn(
	id INTEGER UNSIGNED AUTO_INCREMENT,
    username VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(45) NOT NULL,  
    PRIMARY KEY (id)
);

create table offensive_word(
	id INTEGER UNSIGNED AUTO_INCREMENT,
    word VARCHAR(20) UNIQUE NOT NULL, 
    PRIMARY KEY (id)
);


create table log(
	id INTEGER UNSIGNED AUTO_INCREMENT,
    user_id INTEGER UNSIGNED,
	ts timestamp NOT NULL,
    date_log date NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES usr(id) ON DELETE CASCADE
);

create table product (
	id INTEGER UNSIGNED AUTO_INCREMENT,
    prod_name VARCHAR(30) NOT NULL,
	photoimage longblob,
    PRIMARY KEY (id)
);
  
create table review (
	id INTEGER UNSIGNED AUTO_INCREMENT,
	product_id INTEGER UNSIGNED,
    review_text VARCHAR(400) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);


create table questionnaire (
	id INTEGER UNSIGNED AUTO_INCREMENT,
    date_questionnaire date UNIQUE NOT NULL,
    product_id INTEGER UNSIGNED,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

create table filled_form (
	id INTEGER UNSIGNED AUTO_INCREMENT,
    user_id INTEGER UNSIGNED, 
    questionnaire_id INTEGER UNSIGNED,
    date_form date NOT NULL,
    age INTEGER UNSIGNED DEFAULT NULL,
    sex VARCHAR(10) DEFAULT NULL,
    expertice VARCHAR(150) DEFAULT NULL,
    score INTEGER DEFAULT 0, 
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES usr(id) ON DELETE CASCADE,
	FOREIGN KEY (questionnaire_id) REFERENCES questionnaire(id) ON DELETE CASCADE,
	CONSTRAINT only_one_form UNIQUE(user_id, questionnaire_id)
);


create table question (
	id INTEGER UNSIGNED AUTO_INCREMENT,
	question_text  VARCHAR(150) NOT NULL,
    questionnaire_id INTEGER UNSIGNED,
    PRIMARY KEY(id),
  	FOREIGN KEY (questionnaire_id) REFERENCES questionnaire(id) ON DELETE CASCADE
);

create table answer (
	id INTEGER UNSIGNED AUTO_INCREMENT,
	question_id INTEGER UNSIGNED,
    form_id INTEGER UNSIGNED,
    response VARCHAR(150) DEFAULT NULL,
    PRIMARY KEY(id),
	FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE,
    FOREIGN KEY (form_id) REFERENCES filled_form(id) ON DELETE CASCADE

);

 -- TRIGGER CREATION ----------------------------------------------------------------------------------------------------------------------------------------------------------------------

DELIMITER $$

CREATE TRIGGER compute_points
BEFORE INSERT ON filled_form
FOR EACH ROW
BEGIN
DECLARE age_info, sex_info, exper_info INT;
 
    IF (new.sex is not null  )
		THEN 
			SET sex_info=1;
		ELSE 
			SET sex_info=0;
    END IF;
    
     IF (new.age is not null  )
		THEN 
			SET age_info=1;
		ELSE 
			SET age_info=0;
    END IF;
    
       IF (new.expertice is not null  )
		THEN 
			SET exper_info=1;
		ELSE 
			SET exper_info=0;
    END IF;
    
	SET new.score = 2 * (age_info+ sex_info + exper_info);
    
    UPDATE usr 
    SET daily_points = daily_points + 2 * (age_info+ sex_info + exper_info)
    WHERE id = new.user_id;
    
END $$

DELIMITER ;

-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

DELIMITER $$

CREATE TRIGGER compute_marketing_points
AFTER INSERT ON answer
FOR EACH ROW
BEGIN

   
	UPDATE filled_form
    SET score = score + 1
    WHERE id = new.form_id;
    
    UPDATE usr
    SET daily_points = daily_points + 1
    WHERE id = (SELECT user_id FROM filled_form
				WHERE id = new.form_id)
	AND banned=0;
    
END $$

DELIMITER ;

-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

DELIMITER $$

CREATE TRIGGER prevent_offensive_words
AFTER INSERT ON answer
FOR EACH ROW
BEGIN
		DECLARE banned_user INT;
        DECLARE bannable INT;
	   
		IF EXISTS( SELECT *
		FROM offensive_word 
        WHERE (locate(word, new.response)) > 0
        )
        THEN
         	SELECT  user_id INTO banned_user FROM filled_form 
			WHERE id = new.form_id;
			
            UPDATE usr
			SET banned = 1
			WHERE id = banned_user;
            
			UPDATE usr
			SET daily_points=0
			WHERE id = banned_user;          
            
            -- Deleting the Filled Form causes also the deletion of al the Answers that are linked to it because of the Cascading policy.
			-- DELETE FROM filled_form
            -- WHERE id = new.form_id;
            
		END IF;
END $$

DELIMITER ;
 
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

 
 DELIMITER $$

CREATE TRIGGER remove_scores__after_questionnaire_deletion
AFTER DELETE ON questionnaire 
FOR EACH ROW
BEGIN

		IF(old.date_questionnaire = CURRENT_DATE)
        THEN 
			UPDATE usr
            SET daily_points = 0
            WHERE id>0;
        END IF;
	 
END $$

DELIMITER ;

-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE EVENT reset_daily_score
  ON SCHEDULE
    EVERY 1 DAY
    STARTS '2021-02-26 00:00:00' ON COMPLETION PRESERVE ENABLE 
  DO
    UPDATE usr
    SET totalPoints = 0
    WHERE id > 0;

-- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- SIGNAL sqlstate '45001' set message_text = "No way ! You cannot do this !";
 
drop trigger prevent_offensive_words  ;
drop trigger compute_points ;
drop trigger compute_marketing_points ;

drop trigger  remove_scores__after_questionnaire_deletion;


INSERT INTO usr (username, email, password, banned, daily_points) VALUES ('ludorighi', 'ludo.righi@hotmail.it', 'a', 0, 0);
INSERT INTO usr (username, email, password, banned, daily_points) VALUES ('matte', 'matte@hotmail.it', 'a', 0, 0);
INSERT INTO usr (username, email, password, banned, daily_points) VALUES ('ghera', 'lssss.it', 'a', 0, 0);
INSERT INTO usr (username, email, password, banned, daily_points) VALUES ('france', 'l.....hotmail.it', 'a', 0, 0);
INSERT INTO usr (username, email, password, banned, daily_points) VALUES ('tommi', 'lahahhai@hotmail.it', 'a', 0, 0);


INSERT INTO product (prod_name, photoimage) VALUES ('Playstation 5',_binary'abc');

INSERT INTO review (product_id, review_text) VALUES (1, 'Amazing product!!!!!');
INSERT INTO review (product_id, review_text) VALUES (1, 'Increadibile quality!!!!!');
INSERT INTO review (product_id, review_text) VALUES (1, 'I recommend it to everyone!');

 
INSERT INTO product (prod_name, photoimage) VALUES ('XBox360',_binary'fgh');

INSERT INTO review (product_id, review_text) VALUES (14, 'I love it!');
INSERT INTO review (product_id, review_text) VALUES (14, 'I prefer Playstation honestly..');
INSERT INTO review (product_id, review_text) VALUES (14, 'I cant stop playing!');
INSERT INTO review (product_id, review_text) VALUES (14, 'Too expensive!'); 


INSERT INTO admn (username, email, password) VALUES ('paolo', 'paolo@mail.com', 'a');

INSERT INTO offensive_word (word) VALUES ('cappero');
INSERT INTO offensive_word (word) VALUES ('cavolo');
INSERT INTO offensive_word (word) VALUES ('accipicchia');