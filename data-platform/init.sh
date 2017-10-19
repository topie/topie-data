#!/usr/bin/env bash

mysql -h127.0.0.1 -uroot -proot1234 -e "drop database data_platform;"
mysql -h127.0.0.1 -uroot -proot1234 -e "create database data_platform;"
