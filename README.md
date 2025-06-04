# Aplikacja do wykonywania testów egzaminacyjnych na prawo jazdy

Głównym celem stworzenia aplikacji jest udostępnienie kandydatom na kierowców narzędzia do skutecznej nauki i przygotowania do egzaminu teoretycznego na prawo jazdy.
Aplikacja będzie dostępna jako serwis internetowy, co pozwoli użytkownikom na swobodny dostęp do niej z różnych urządzeń bez konieczności instalowania dodatkowego oprogramowania.<br>
Jednym z kluczowych założeń aplikacji jest zachowanie identyczności względem egzaminu państwowego poprzez korzystanie z tych samych pytań oraz zasad tworzenia i oceniania egzaminu.<br>
Konsekwencją stworzenia aplikacji jest nabycie doświadczenia w procesie projektowania i tworzenia aplikacji internetowych z wykorzystaniem języka Java.

# Analiza wymagań
W ramach tworzenia aplikacji przeanalizowałem wymagania tego typu aplikacji oraz podzieliłem je na trzy główne kategorie:
- **funkcjonalne**
- **niefukcjonalne**
- **zgodności**

W trakcie modelowania działania aplikacji posłużyłem się diagramami UML przedstawiając przypadki użycia aplikacji dla trzech aktorów:
- **anonimowy użytkownik**
- **użytkownik**
- **administrator**

## Anonimowy użytkownik
![anonimowy_uzytkownik](https://github.com/user-attachments/assets/87b7e392-728f-4f94-a311-8493d50563ac)

## Użytkownik
![użytkownik](https://github.com/user-attachments/assets/bef311c8-23c5-4c96-afc2-15b6af07d99a)

## Administrator
![Administrator](https://github.com/user-attachments/assets/6ea694f1-17c6-4dd9-8097-38f0c43ae30e)

# Projekt

## Architektura systemu

Aplikacja powstała w oparciu o wzorzec architektoniczny **MVC**.<br>
Struktura projektu:<br>
![image](https://github.com/user-attachments/assets/ca6acaca-1a44-456c-bb17-7a98ecf820e6)



## Baza danych

Największe doświadczenie mam z bazami danych MySQL dlatego wybrałem **MariaDB**.

### Diagram związków encji w relacyjnej bazie danych

Dla lepszego zrozumienia i zademonstrowania architektury modeli danych używanych w aplikacji stworzyłem diagram związków encji.

![image](https://github.com/user-attachments/assets/a3318296-cbbd-4fab-9ca0-2239bb991f8c)

### Opis poszczególnych tabel

![image](https://github.com/user-attachments/assets/82461480-c6c2-46cb-b960-a867f22082e4)

![image](https://github.com/user-attachments/assets/822ff5fe-4bce-4fa1-852e-a95aa613b845)

![image](https://github.com/user-attachments/assets/c5177438-a957-44bc-91d2-a7279fa90452)

![image](https://github.com/user-attachments/assets/c82b626f-8f01-4785-922c-8e41067550e4)

![image](https://github.com/user-attachments/assets/17eab800-3c14-461e-b26c-2b24a7c7eea4)

![image](https://github.com/user-attachments/assets/b16e2185-d784-44e6-8aff-43e7946dd333)

![image](https://github.com/user-attachments/assets/ebabe6ed-a426-42d3-8446-7468bc89270e)

![image](https://github.com/user-attachments/assets/8300fa37-df3a-45f4-9f3f-c23eb9f5d18c)

![image](https://github.com/user-attachments/assets/37245bb1-4e85-4d4b-8eae-541c7f7dfcac)

![image](https://github.com/user-attachments/assets/abeaecd8-cf20-4c51-9628-db2ecec49dcc)

![image](https://github.com/user-attachments/assets/dc8ae893-05fe-408a-8c22-4047b3662ff2)

![image](https://github.com/user-attachments/assets/c6c6d2b7-b33c-4c39-a338-4decea7fcd15)

# Backend

Wykorzystany języka programowania to **Java 17** wraz z frameworkiem **Spring**.<br>
Do napisania systemu posłużyło mi **Intellij IDEA**.<br>
Aplikacja była budowana z użyciem narzędzia **Maven**.

# Frontend

Jako silnik szablonów wykorzystałem **Thymealeaf**.<br>
Skrypty pomocnicze pisałem z użyciem **JavaScript**.


