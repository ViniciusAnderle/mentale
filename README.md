Mentale - Plataforma de Telemedicina Psicológica
Este projeto é uma aplicação completa de telemedicina voltada à psicologia, permitindo o cadastro, login e autenticação segura de usuários, com frontend em Vue.js e backend em Spring Boot. A infraestrutura é gerenciada com Docker e Docker Compose.

Estrutura do Projeto
mentale/
├── backend/         # Spring Boot + Maven
│   ├── src/... 
│   └── Dockerfile
├── frontend/        # Vue.js (com Vite)
│   ├── src/... 
│   └── Dockerfile
├── docker-compose.yml
└── README.md

Como rodar a aplicação

Pré-requisitos
    • - Docker
- Docker Compose
- Java 17
- Node.js

Passo a passo
1. Acesse o diretório do projeto:
cd mentale
2. Compile o backend:
cd backend
./mvnw clean package -DskipTests
cd ..
3. Suba tudo com Docker:
docker-compose up --build
A aplicação estará disponível em:
• - Frontend: http://localhost:3000
- Backend: http://localhost:8080
- Banco de Dados: localhost:5432 (PostgreSQL)

 
💡 Dicas
Para reiniciar os containers:
./mvnw clean package -DskipTests
docker-compose up --build
