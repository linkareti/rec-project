#!/bin/bash

cmd="dockerize"

cmd="$cmd -timeout 240s httpd-foreground"

$cmd