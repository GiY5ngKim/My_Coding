## DB 실행 (Docker)

```bash
cd infra
docker compose up -d
```

---

## Environment Variables (IntelliJ 실행 기준)

IntelliJ → Run → Edit Configurations → Environment variables 에 아래 값 그대로 추가:

```
DB_URL=jdbc:postgresql://localhost:5433/moyeo
DB_USERNAME=moyeo
DB_PASSWORD=moyeo123
```


## OAuth2 Redirect URL 설정

로컬 테스트:
OAUTH2_REDIRECT_URL=http://localhost:8080/oauth/callback

프론트 연동:
OAUTH2_REDIRECT_URL=http://localhost:3000/oauth/callback

배포:
OAUTH2_REDIRECT_URL=https://moyeo.com/oauth/callback


http://localhost:8080/oauth2/authorization/google
