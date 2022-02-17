API for automatic assignment of food orders to available couriers.

How to run the project?
    The bellow command is sufficient.
    docker-compose up --build
How to run only the backend part?
    go to spring_boot_api folder and run the commands
    ./gradlew build
    java -jar .\build\libs\courier-0.0.1-SNAPSHOT.jar 
How to run only the User Interface?
    go to the user_interface folder an run the command
    npm start
How to access the User Interface?
    Access http://localhost:3000/ in browser
How to send the http request to backend.
    For this application I created http://localhost:8080/assignCouriers endpoint (POST http request). 
Required dependency/technology to run the project.
    To run the project locally you need to install docker-compose and docker

Detalii:
    Pentru proiectul propus au fost indeplinite toate cerintele. Pe langa cerintele implementate am creat un user-interface 
    si am integrat proiectul in Docker si docker-compose pentru a putea vizualiza usor proiectul si pentru a avea un proces de deployment cat mai usor.
    Tehnologii:
        Backend:
            - Spring Boot + gradle
        User Interface:
            - React + NodeJs + Bootstrap + multiple librarii aditionale
        Testarea:
            - Postman si cereri http
            - Au fost adaugate teste care acopera majoritatea cazurilor de baza. Testele se afla in spring_boot_api\src\test\java\com\tazz.
            - Au fost adaugate 7 teste de intrare si 7 rezultate asteptate.
Algoritm:
    Algoritmul implementat este o abordarea a fluxului maxim in cadrul unui graf bipartit. 
    Exista 3 abordari a algoritmului in folderul algorithm.
    
    - Prima descrie doar o abordarea a fluxului maxim in cadrul unui graf bipartit. Aceasta abordare nu selecteaza optiunea cea mai buna 
    din punct de vedere a kilometrilor in cazul in care exista multiple optiuni de a selecta curierii.
    
    - A doua solutie si cea de baza este un improve adus primului algoritm bazat pe o metoda Greedy. Aceasta abordare selecteaza optiunea cea 
    mai buna din punct de vedere a kilometrilor in cazul in care exista multiple optiuni de a selecta curierii. Aceasta solutie ar putea sa dea
    rezultate gresite in rare cazuri insa complexitatea si timpul de rulare ofera avantaj pentru aceasta solutie in cazul datelor mari.

    - A treia solutie este doar un pseudocod al algoritmului care ar rezolva problema pentru toate cazurile. Abordarea s-ar baza pe Hungarian 
    algorithm pentru un unbalanced bipartite graph.

Chestii fancy :))
    - Serializarea JSON - ului primit. Structura obiectelor clara.
    - User interface responsive. (check it on a phone mode)
    - Docker. (rularea programului se face printr o singura comanda)
    - Testarea (foarte usor de adaugat alte teste)
    - USER interface-ul se poate accesa la adresa https://tazz.netlify.app/ (Partea de backend nu a fost adaugata. Pentru a vizualiza trebuie facut deploymentul local)

Notite:
	- Testarea de erori
	- Lipsa unor fielduri,
	- pickup si delivery cam useless, ne asumam ca e doar un pickup si un delivery

Multe detalii nu au fost acoperite in acest fisier. In caz de necesitate sunt dispus sa ofer mai multe detalii in cazul unor intrebri
