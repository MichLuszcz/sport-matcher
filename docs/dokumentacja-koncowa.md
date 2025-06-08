# 1. Temat projektu

Projekt „Sport Matcher” został stworzony z myślą o osobach aktywnych fizycznie, które szukają partnerów do wspólnego
uprawiania sportu. Głównym celem było opracowanie aplikacji webowej umożliwiającej użytkownikom tworzenie ogłoszeń
sportowych oraz przeszukiwanie istniejących ofert według różnych kryteriów, takich jak typ sportu, lokalizacja czy
liczba uczestników. System miał być prosty, intuicyjny w obsłudze, a jednocześnie zapewniać funkcjonalności niezbędne do
bezpiecznego i skutecznego nawiązywania kontaktów sportowych.

# 2. Zespół projektowy

Michał Łuszczek - Backend
Konrad Solarski - Frontend
Julia Ciszewska - Frontend i pomoc z Backendem

# 3. Założenia funkcjonalne

Główne założenia funkcjonalne obejmowały:

- możliwość rejestracji i logowania użytkowników,
- dodawanie i edytowanie ogłoszeń,
- przeglądanie listy wydarzeń sportowych,
- zarządzanie profilem użytkownika,
- filtrowanie ogłoszeń po dyscyplinie sportowej oraz przeszukiwanie opisów,
- zabezpieczenie danych użytkowników oraz tokenową autoryzację dostępu do wybranych endpointów

# 4. Założenia architektoniczne

Od strony architektonicznej zdecydowano się na model aplikacji internetowej typu SPA (Single Page Application), w którym
warstwa frontendowa (React + Vite) komunikuje się z backendem (Spring Boot) za pośrednictwem REST Api.
Backend odpowiada za przechowywanie danych, logikę biznesową oraz walidację wejścia. Całość działa w układzie
klient–serwer, przy czym klient może działać niezależnie od serwera, a połączenie odbywa się z uwzględnieniem nagłówków
autoryzacji JWT. Struktura bazy danych została zaprojektowana tak, aby wspierać rozwój aplikacji – z osobnymi tabelami
dla użytkowników, ogłoszeń, typów sportów, wiadomości między użytkownikami oraz próśb o dołączenie.

# 5. Narzędzia i technologie

Postman (do testowania API), GitHub (jako repozytorium kodu) oraz Mysql jako system bazodanowy. W zakresie
technologii frontendowych użyto React, Vite, TypeScript. Backend wykorzystuje Spring Boot, Spring
Security, JPA, Hibernate, JWT oraz bibliotekę Lombok do automatyzacji kodu Java.

# 6. Proces realizacji

Proces tworzenia projektu rozpoczął się od zaprojektowania schematu bazy danych i relacji pomiędzy encjami. Następnie
stworzono logikę backendową z podziałem na warstwy: encje, repozytoria, serwisy i kontrolery. W kolejnym kroku
zaimplementowano frontend z obsługą routingu, komponentów oraz formularzy. Istotnym elementem prac była integracja
warstw, a więc połączenie aplikacji React z API przy użyciu fetch i przekazywaniem tokenów JWT w nagłówkach.

# 7. Opis techniczny

Po stronie backendu zdefiniowano następujące główne encje: User, Ad, SportType, JoinRequest i Message. Każda z nich
została wyposażona w
dedykowane repozytorium JPA, a logika biznesowa została umieszczona w serwisach. Endpointy REST zostały zaimplementowane
w kontrolerach AuthController i po jednym kontrolerze odpowiadającym każdej encji. Zastosowano konfigurację Spring
Security z filtrem JWT
oraz ograniczeniem dostępu do wybranych ścieżek. Frontend składa się z komponentów pozwalających przeglądanie wszystkich
ogłoszeń, logowanie, rejestrację, tworzenie własnego ogłoszenia. Komunikacja z API odbywa się poprzez fetch oraz hooki
Reacta (useEffect, useState), a routing obsługuje biblioteka react-router-dom.

## Opis modułów:

### 1. Moduł User

Wewnętrzna konstrukcja:
Moduł User zarządza kontami użytkowników. Centralną encją jest User, która przechowuje takie dane jak id, username,
email, password, name, dateCreated, isActive i role. Klasa User posiada relacje OneToMany do ogłoszeń (Ad), wysłanych
wiadomości (Message jako sender) i otrzymanych wiadomości (Message jako receiver). Klasa User zawiera metodę prePersist,
która automatycznie ustawia datę utworzenia konta na bieżący czas.

Interfejs do innych modułów:

`UserController`: Udostępnia punkty końcowe API do zarządzania użytkownikami.

- `POST /api/users`: Rejestruje nowego użytkownika.
- `GET /api/users/{id}`: Pobiera dane użytkownika po ID.
- `GET /api/users`: Pobiera listę wszystkich użytkowników.
- `DELETE /api/users/{id}`: Usuwa użytkownika.

`UserService`: Warstwa serwisowa obsługująca logikę biznesową dla użytkowników.

`UserPrincipal`: Używane w module security do przechowywania szczegółów uwierzytelnionego użytkownika.

### 2. Moduł Ad

Wewnętrzna konstrukcja:
Moduł Ad odpowiada za zarządzanie ogłoszeniami. Encja Ad zawiera takie pola jak id, user (użytkownik tworzący
ogłoszenie), sportType, title, description, dateStart, dateEnd, timeStart, timeEnd, location, participants, isActive,
creationDatetime. Encja Ad posiada relację ManyToOne do User i SportType, oraz OneToMany do JoinRequest. Metoda
prePersist automatycznie ustawia datę utworzenia ogłoszenia.

Interfejs do innych modułów:

`AdController`: Udostępnia punkty końcowe API do zarządzania ogłoszeniami.

- `POST /api/ads`: Tworzy nowe ogłoszenie.
- `GET /api/ads`: Pobiera listę wszystkich ogłoszeń.
- `GET /api/ads/{id}`: Pobiera ogłoszenie po ID.
- `GET /api/user/{userId}/ads`: Pobiera ogłoszenia utworzone przez konkretnego użytkownika.
- `DELETE /api/ads/{id}`: Usuwa ogłoszenie.

`AdService`: Warstwa serwisowa obsługująca logikę biznesową dla ogłoszeń.

### 3. Moduł JoinRequest

Wewnętrzna konstrukcja:
Moduł JoinRequest zarządza zgłoszeniami użytkowników do wydarzeń z ogłoszeń. Encja JoinRequest zawiera id, user (
zgłaszający się użytkownik), ad (ogłoszenie, do którego użytkownik się zgłasza), status (PENDING, ACCEPTED, REJECTED)
oraz createdAt. Metoda prePersist automatycznie ustawia datę wysłania prośby.

Interfejs do innych modułów:

JoinRequestController: Udostępnia punkty końcowe API do zarządzania zgłoszeniami dołączenia.

- `POST /api/ads/{adId}/join-requests`: Tworzy nowe zgłoszenie dołączenia do ogłoszenia.
- `GET /api/join-requests`: Pobiera listę wszystkich zgłoszeń dołączenia.
- `GET /api/join-requests/{id}`: Pobiera zgłoszenie dołączenia po ID.
- `GET /api/users/{userId}/join-requests`: Pobiera zgłoszenia wysłane przez konkretnego użytkownika.
- `GET /api/ads/{adId}/join-requests`: Pobiera zgłoszenia do konkretnego ogłoszenia.
- `PUT /api/join-requests/{id}/accept`: Akceptuje zgłoszenie dołączenia.
- `PUT /api/join-requests/{id}/reject`: Odrzuca zgłoszenie dołączenia.
- `DELETE /api/join-requests/{id}`: Usuwa zgłoszenie dołączenia.

`JoinRequestService`: Warstwa serwisowa obsługująca logikę biznesową dla zgłoszeń dołączenia.

### 4. Moduł Message

Wewnętrzna konstrukcja:
Moduł Message odpowiada za komunikację między użytkownikami. Encja Message zawiera id, sender, receiver, content oraz
sentAt. Posiada relacje ManyToOne do User (dla nadawcy i odbiorcy). Metoda prePersist automatycznie ustawia datę i czas
wysłania wiadomości.

Interfejs do innych modułów:

`MessageController`: Udostępnia punkty końcowe API do zarządzania wiadomościami.

- `POST /api/messages`: Tworzy nową wiadomość.
- `GET /api/messages/{id}`: Pobiera wiadomość po ID.
- `GET /api/users/{userId}/messages/sent`: Pobiera wiadomości wysłane przez konkretnego użytkownika.
- `GET /api/users/{userId}/messages/received`: Pobiera wiadomości odebrane przez konkretnego użytkownika.
- `DELETE /api/messages/{id}`: Usuwa wiadomość.

`MessageService`: Warstwa serwisowa obsługująca logikę biznesową dla wiadomości.

### 5. Moduł SportType

Wewnętrzna konstrukcja:
Moduł SportType służy do kategoryzacji ogłoszeń według rodzaju aktywności fizycznej. Encja SportType ma id i name.

Interfejs do innych modułów:
`SportTypeController`: Udostępnia punkty końcowe API do zarządzania typami sportów.

- `GET /api/sport-types`: Pobiera listę wszystkich typów sportów.
- `GET /api/sport-types/{id}`: Pobiera typ sportu po ID.
- `GET /api/sport-types/name/{name}`: Pobiera typ sportu po nazwie.

`SportTypeService`: Warstwa serwisowa obsługująca logikę biznesową dla typów sportów.

# 8. Instalacja

Aby uruchomić backend, należy wykonać poniższe komendy

1. W `/backend/sportmatcher/`: `./gradlew build -x test`
2. W `/deployment/`: `docker compose -f ./docker-compose-dev.yaml up --build`
3. Api serwera dostępne jest pod `http://localhost:8080/api`

Frontend wymaga zainstalowania Node.js oraz wykonania komend `npm install`, a następnie `npm run dev`, co uruchomi
serwer
developerski na `http://localhost:5173`. W celu poprawnego działania obu warstw, backend musi być aktywny i dostępny pod
odpowiednim
adresem, zgodnym z konfiguracją w frontendzie.

# 9. Instrukcja użytkownika

Korzystanie z aplikacji jest intuicyjne. Po wejściu na stronę użytkownik może się zarejestrować i zalogować. Po
zalogowaniu otrzymuje dostęp do pełnej listy ogłoszeń, które może przeglądać, filtrować lub wyszukiwać. Może również
dodać własne ogłoszenie sportowe poprzez formularz, a następnie przeglądać i usuwać swoje oferty w zakładce „My
Profile”. Panel profilu umożliwia także edycję danych oraz wylogowanie z aplikacji.

# 10. Podsumowanie

Aplikacja „Sport Matcher” to platforma do ogłaszania wydarzeń sportowych, która została
zaprojektowana i zaimplementowana zgodnie z nowoczesnymi standardami tworzenia aplikacji webowych. Projekt zawiera
zarówno warstwę logiczną (backend), jak i użytkową (frontend), a jego elastyczna architektura pozwala na dalszy rozwój,
np. o system wiadomości, czat, powiadomienia e-mail lub system oceniania użytkowników.
