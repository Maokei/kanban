FROM node:15.4.0-alpine3.12 as build 

WORKDIR /app

COPY package.json yarn.lock ./
RUN yarn install

COPY . ./

#ARG REACT_APP_API_BASE_URL
#ENV REACT_APP_API_BASE_URL=$(REACT_APP_API_BASE_URL)

RUN yarn build

FROM nginx:alpine

# Copy from build container
COPY --from=build /app/build /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
