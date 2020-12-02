<?php

    include("WordFinder.php");

    //check command to run script has 3 arguments
    if($argc == 3){
        $fileName = $argv[1];
        $searchString = $argv[2];
        $scriptArgumentsValid = checkScriptArgumentsValid($fileName, $searchString);
        if($scriptArgumentsValid){
            $checkTextFileContentIsValid = checkTextFileContentIsValid($fileName);
            if($checkTextFileContentIsValid){
                $wordFinder = new WordFinder($fileName);
                $longestWord = $wordFinder->longestWord($searchString);
                if($longestWord !== ""){
                    echo "\nSearch Complete... Match was found! The longest word possible is " . $longestWord . "\n\n";
                }
                else{
                    echo "\nSearch Complete.... No match was found\n\n";
                }
            }
        }
    }
    else{
        echo "\nIncorrect amount of arguments are being used to run the script.\n";
        echo "Two command line arguments are required to run this program.\n";
        echo "The first argument is the text file name, the second argument is a max 12 character string\n\n";
    }

    /* check the command line arguments are valid, the first command line argument should be a text file, 
    the second command line argument should be an alphabetic string containing 12 characters or less */
    function checkScriptArgumentsValid($fileName, $searchString){
        if(preg_match('/.txt$/', $fileName) == false){
            echo "\nThe first argument fileName must be a .txt file\n\n";
            return false;
        }
        if(ctype_alpha($searchString) == false || strlen($searchString) > 12){
            echo "\nThe second argument must be an alphabetic string containing 12 characters or less\n\n";
            return false;
        }
        return true;
    }

    //check text file content is valid, that the file exists and the text file only contains alphabetic characters
    function checkTextFileContentIsValid($fileName){
        if (file_exists($fileName)) {
            $handle = fopen($fileName, "r");

            while (($line = fgets($handle)) !== false) {
                if (ctype_alpha(trim($line)) == false || strpos(trim($line), ' ') == true) {
                    echo "\nThe file must only contain letters and one word per line\n\n";
                    return false;
                }
            }
            fclose($handle);
            return true;
        }
        else{
            echo "\nFile does not exist in the location specificed, please recheck the path entered in the command line to ensure the file exists there\n\n";
            return false;
        }
    }


?>