<?php

    include("TaskCombinations.php");

    echo("\nPlease enter a list of strings in the format [\"T=V1,V2,V3,...\", \"U=W1,W2,W3,...\"]\n");
    echo( "Each string B starts with a token name T, followed by the symbol ‘=' and a comma-separated list of values {Vx}\n");
    $combinationsInput = readline('Enter a string: ');
    $isCombinationsInputCorrect = checkCombinationsInputIsValid($combinationsInput);
    if($isCombinationsInputCorrect){
        $taskCombinations = new TaskCombinations();
        $uniqueCombinations = $taskCombinations -> generateUniqueCombinations($combinationsInput);
        echo("\nString entered is valid, printing combinations: \n\n");
        foreach($uniqueCombinations as $uniqueCombination){
             echo($uniqueCombination);
             echo("\n");
        }
        echo("\n");
    }
    else{
        echo("\nThe entered string is not in the correct format...\n");
        echo("\nPlease enter a list of strings in the format [\"T=V1,V2,V3,...\", \"U=W1,W2,W3,...\"]\n");
        echo("\nAn example would be [\"a=a1,a2,a3\",\"b=b1,b2,b3\",\"c=c1,c2,c3\"]\n\n");
    }

    //checks that list of strings entered matches the required format
    function checkCombinationsInputIsValid($combinationsInput){
        if(preg_match("/^\[(\"\w=(\w\d,)*\w\d\",){1,}\"\w=(\w\d\,)*\w\d\"\]$/", $combinationsInput)){
            return true;
        }
        else{
            return false;
        }
    }

?>