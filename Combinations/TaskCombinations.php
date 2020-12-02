<?php

    class TaskCombinations{
        function generateUniqueCombinations($combinationsInput){
            $tokenValueCombinations = $this -> generateTokenValueCombinations($combinationsInput);
            $uniqueCombinationsData = $this -> buildUniqueCombinations($tokenValueCombinations);
            $uniqueCombinations = $this -> convertToUniqueStrings($uniqueCombinationsData);
            return $uniqueCombinations;
        }

        function generateTokenValueCombinations($combinationsInput){
            $tokenValueCombinations = array();
            $combinationsInput = json_decode($combinationsInput);
            $currentTokenValues = array();
            $currentToken = "";
            foreach($combinationsInput as $combinationInput){
                //regex to extract token and all values from input
                preg_match("/\w=/", $combinationInput, $tokenResult);
                preg_match("/=\w\d/", $combinationInput, $firstValueResult);
                preg_match_all("/(?<!=)\w\d\,/", $combinationInput, $middleValuesResults);
                preg_match("/\w\d(?!,)/", $combinationInput, $lastValueResult);

                $token = $tokenResult[0];
                //if we're checking the first token it needs to become the current token at the start
                if($currentToken == ""){
                    $currentToken = $token;
                }
                /* if the current token is not equal to the token being checked we add the currentTokenValues to the list and
                the next token becomes the current one to be checked */
                if($currentToken!=$token){
                    $currentToken = $token;
                    $tokenValueCombinations [] = $currentTokenValues;
                    $currentTokenValues = array();
                }
                
                //get the first middle and last values for each specific token to build our list of values for the current token
                $firstValue = $this -> filterString($firstValueResult[0]);
                $currentTokenValues [] = $token . $firstValue;
                $middleValues = $middleValuesResults[0];
                foreach($middleValues as $middleValue){
                    $currentTokenValues [] = $token . $this -> filterString($middleValue);
                }
                $lastValue = $this -> filterString($lastValueResult[0]);
                $currentTokenValues [] = $token . $lastValue;
            }
            //needed to add the last tokens values to the list
            $tokenValueCombinations [] = $currentTokenValues;
            return $tokenValueCombinations;
        }

        //remove non alphanumeric characters from string
        function filterString($string){
            $filteredString = preg_replace("/[^A-Za-z0-9 ]/", '', $string);
            return $filteredString;
        }

        function buildUniqueCombinations($tokenValueCombinations, $i = 0){

            if ($i == count($tokenValueCombinations) - 1) {
                return $tokenValueCombinations[$i];
            }
        
            /* get the tokenValueCombinations for previously checked array to check combinations against the current one,
            each iteration builds the uniqueCombinationsData */
            $tempTokenValueCombinations = $this -> buildUniqueCombinations($tokenValueCombinations, $i + 1);
        
            $uniqueCombinationsData = array();
        
            //loop through the current tokenValueCombinations and check each one against all the previous tokenValueCombinations
            foreach ($tokenValueCombinations[$i] as $tokenValueCombination) {
                foreach ($tempTokenValueCombinations as $tempTokenValueCombination) {
                    //merge the sorted tokenValueCombinations with the ones currently being sorted
                    if(is_array($tempTokenValueCombination)){
                        $uniqueCombinationsData[] = array_merge(array($tokenValueCombination), $tempTokenValueCombination);
                    }
                    //merge the current and the previous tokenValueCombination if its the first iteration through
                    else{
                        $uniqueCombinationsData[] = array($tokenValueCombination, $tempTokenValueCombination);
                    }
                }
            }
        
            return $uniqueCombinationsData;
        }

        //convert uniqueCombinations from arrays to string
        function convertToUniqueStrings($uniqueCombinationsData){
            $uniqueString = "[\n";
            $uniqueCombinations = array();
            foreach($uniqueCombinationsData as $uniqueCombinationData){
                $uniqueString .= "\"";
                foreach($uniqueCombinationData as $valueToken){
                    $uniqueString .= $valueToken . "&";
                }
                //remove the extra & at the end of each unique string
                $uniqueString = substr_replace($uniqueString, "\",", strlen($uniqueString)-1);
                $uniqueCombinations [] = $uniqueString;
                $uniqueString = "";
            }
            //remove the extra "," from the final unique string
            $lastCharacterUniqueCombination = $uniqueCombinations[count($uniqueCombinations) - 1];
            $uniqueCombinations[count($uniqueCombinations) - 1] = substr_replace($lastCharacterUniqueCombination, "\n]", strlen($lastCharacterUniqueCombination)-1);
            return $uniqueCombinations;
        }
    }
?>