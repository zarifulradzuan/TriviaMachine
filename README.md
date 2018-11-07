This is a simple java game application that uses questions retrieved from https://opentdb.com/.

1)	Parameters that the API is able to receive. 
  
	https://opentdb.com/api.php
    
		i.)	amount: The number of questions wanted
    ii.)	difficulty: Difficulty of questions returned
    iii.)	token: Token that can be used to track question answered so there would be no duplicate
    iv.)	type: Type of questions, whether true or false or multiple choice

  https://opentdb.com/api_token.php
    i.)	command: command that can be given are request (request new token) and reset (reset questions answered)

  https://opentdb.com/api_category.php
    i.)	No parameters, it only return a list of categories.

2)	Responses that the API return (the JSON values).
  a.	https://opentdb.com/api.php
    i.)	response_code
    ii.)	results
      1.	category
      2.	type
      3.	difficulty
      4.	question
      5.	correct_answer
      6.	incorrect_answers

  b.	https://opentdb.com/api_token.php
    i.)	response_code
    ii.)	response_message
    iii.)	token

  c.	https://opentdb.com/api_category.php
    i.)	trivia_categories
      1.	id
      2.	name
