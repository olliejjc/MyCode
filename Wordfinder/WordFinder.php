<?php

class Wordfinder{

    private $fileName;

    function __construct($fileName){
        $this -> fileName = $fileName;
    }

    function longestWord($searchString){
        $longestWord = "";
        $characterCount = 0;
        $fileContent = file_get_contents($this->fileName);
        $separator = "\r\n";
        $line = strtok($fileContent, $separator);
        $searchStringArray = str_split($searchString);
        //loop to check each line in the file
        while ($line !== false) {
            $line = strtok($separator);
            $wordLength = strlen($line);
            $charactersAlreadyChecked = array();
            foreach($searchStringArray as $char){
                //Check if character exists in word to be checked and that the character hasn't already been checked
                if(strpos(trim($line), trim($char)) !== false && !in_array($char, $charactersAlreadyChecked)){
                    $characterCountInWord = substr_count($line, $char); 
                    $characterCountInSearchString = substr_count($searchString, $char);
                    //check how many times the character appears in both the search string and the word
                    $characterCountInWord = substr_count($line, $char); 
                    $characterCountInSearchString = substr_count($searchString, $char);
                    //The character count will increase by the amount of times the character appears simultaneously in the word and the search string
                    if($characterCountInWord >= 1 && $characterCountInSearchString >=1){
                        if($characterCountInWord < $characterCountInSearchString){
                            $characterCount += $characterCountInWord;
                        }
                        else if($characterCountInSearchString < $characterCountInWord){
                            $characterCount += $characterCountInSearchString;
                        }
                        else{
                            $characterCount += $characterCountInWord;
                        }
                        /*Once a specific character is matched to the word we don't need to check the same character from the search string
                        if it appears again*/
                        $charactersAlreadyChecked[] = $char;
                    }
                }
                //if the count of characters found in the word matches the word length this means the word exists in the search string
                if($characterCount == $wordLength){
                    //if the current word is longer than the previous longest word it becomes the new longest word
                    if(strlen($line) > strlen($longestWord)){
                        $longestWord = $line;
                    }
                    break;
                }
            }
            $characterCount = 0;
        }
        return $longestWord;
    }
}

?>