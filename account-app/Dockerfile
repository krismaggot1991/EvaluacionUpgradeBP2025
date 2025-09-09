# ====== Build ======
FROM node:20-alpine AS build
WORKDIR /app

# Instala deps
COPY package*.json ./
RUN npm ci

# Copia el resto del código y construye
COPY . .
# Si usas styles.scss en angular.json, ya queda tomado por el build
RUN npm run build

# ====== Runtime (Nginx) ======
FROM nginx:alpine
# Config de Nginx con proxy /api -> gateway
COPY docker/nginx.conf /etc/nginx/conf.d/default.conf

# Copia artefactos Angular (con builder moderno la salida suele ser /dist/<app>/browser)
COPY --from=build /app/dist/account-app/browser /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
