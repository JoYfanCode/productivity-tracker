FROM postgres:latest

RUN localedef -i de_DE -c -f UTF-8 -A /usr/share/locale/locale.alias de_DE.UTF-8
ENV LANG ru_RU.utf8
ENV LANG=C.UTF-8
ENV LANG=en_US.UTF-8

ENV POSTGRES_DB=activity_tracker
ENV POSTGRES_USER=joyfan
ENV POSTGRES_PASSWORD=12345678
EXPOSE 5432 