-- 1) PREVENT OFFENSIVE WORDS and SET TO ZERO POINT IF BANNED -----------------------------------------------------------


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
            
		END IF;
END $$

DELIMITER ;


-- 2) RESET POINT EVERY MIDNIGHT -------------------------------------------------------------------------------------------------------------------------------------------

 CREATE EVENT reset_daily_score
  ON SCHEDULE
    EVERY 1 DAY
    STARTS '2021-02-26 00:00:00' ON COMPLETION PRESERVE ENABLE 
  DO
    UPDATE usr
    SET totalPoints = 0
    WHERE id > 0;


-- 3) REMOVE POINT AFTER THE DELETION OF THE QUESTIONNAIRE ---------------------------------------------------------------------------------------------------


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

-- 4) COMPUTE POINTS ---------------------------------------------------------------------------------------------------


DELIMITER $$

CREATE TRIGGER compute_points
BEFORE INSERT ON filled_form
FOR EACH ROW
BEGIN
DECLARE age_info, sex_info, exper_info, num_questions INT;
 
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
    
    SELECT COUNT(*) INTO num_questions FROM QUESTIONNAIRE Q
    JOIN QUESTION H 
    ON Q.id = H.questionnaire_id
    WHERE Q.date_questionnaire=CURRENT_DATE;
 
	SET new.score = 2 * (age_info+ sex_info + exper_info) + num_questions;
    
    UPDATE usr 
    SET daily_points =  2 * (age_info+ sex_info + exper_info) + num_questions
    WHERE id = new.user_id;
    
END $$

DELIMITER ;