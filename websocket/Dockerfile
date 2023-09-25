FROM node:18.15.0
RUN mkdir -p /app
COPY package.json /app/
WORKDIR /app
ADD . /app
RUN npm install
RUN npm install -g pm2
EXPOSE 3000
CMD [ "pm2-runtime", "start", "index.js" ]