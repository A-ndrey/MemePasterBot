#!/bin/bash
read -r KPID < app.pid
kill -15 "$KPID"