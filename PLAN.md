J'ai décidé de rédiger un plan pour indiquer comment j'aurais refactoriser ce code car je n'ai pas eu le temps d'avancer comme je le souhaitais :


-Ajout du package model + refactoring des models (sortir les models du controlleur et modifier les noms des variables pour que ça soit plus explicite)

-Ajout du package service + deplacer tout le code du controlleur dans le service car le traitement ne doit être fait que dans le service

-Ajout des tests unitaires + tests d'intégrations (à faire avant le refactoring pour être sûr que ça marche bien après le refactoring)

-Refactoring de la méthode getPrice du service :

1/ Ajout d'un test pour vérifier si body est null > renvoie exception

2/ Ajout d'un enum TypeClient(STANDARD_CUSTOMER, PREMIUM_CUSTOMER, PLATINUM_CUSTOMER) et inverser tous les if pour éviter les NullPointerException

3/ Pour le calcul du prix, enlever le if/else car code identique dedans et en plus condition presque identique, ajout d'un boolean
isDiscountPeriods = cal.get(Calendar.DAY_OF_MONTH) < 15 &&
cal.get(Calendar.DAY_OF_MONTH) > 5 &&
(cal.get(Calendar.MONTH) == 5 || cal.get(Calendar.MONTH) == 0)

4)Ajout d'une methode qui renvoie la valeur du discount > 1 si isDiscountPeriods false sinon en fonction du type de item, renvoie la bonne valeur (TSHIRT -> 1, DRESS -> 0.8 etc)

5

            if (body.getItems() == null) {
             return "0";
             }
                for (int i = 0; i < body.getItems().length; i++) {
                   Item it = body.getItems()[i];

                   if (it.getType().equals("TSHIRT")) {
                       price += 30 * it.getQuantity() * discount;
                   } else if (it.getType().equals("DRESS")) {
                       price += 50 * it.getQuantity() * method(boolean,DRESS) * discount;
                   } else if (it.getType().equals("JACKET")) {
                       price += 100 * it.getQuantity() * method(boolean,JACKET) * discount;
                   }
                   // else if (it.getType().equals("SWEATSHIRT")) {
                   //     price += 80 * it.getNb();
                   // }
               }

6) Ajout d'une methode @ExceptionHandler dans le controlleur pour gérer les exceptions

