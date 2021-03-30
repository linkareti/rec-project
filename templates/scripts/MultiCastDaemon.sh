#!/bin/bash
#
# multicast_@lab.name@      This shell script takes care of starting and stopping multicast_@lab.name@ daemon
#
# chkconfig: 35 91 9
#
### BEGIN INIT INFO
# Provides: multicast_@lab.name@
# Required-Start: $network $syslog $glassfish
# Required-Stop: $network $syslog
# Default-Start:
# Default-Stop:
# Description: Starts and stops the multicast_@lab.name@ daemon
# Short-Description: start and stop multicast_@lab.name@
### END INIT INFO

BASE_USER=@daemon.user@
BASE_USER_HOMEDIR=~@daemon.user@
CURDIR=$(cd `dirname $0` && pwd)
CURUSER=`id -nu`

RETVAL=0

start_service() {
  echo "Starting multicast_@lab.name@ $BASE_USER Service"
  if [ "x$CURUSER" != "x$BASE_USER" ]; then
    `sudo -u $BASE_USER sh StartMultiCastController.sh &>/dev/null`
  else
    `sh $CURDIR/StartMultiCastController.sh &>/dev/null`
  fi
  RETVAL=$?
  if [ $RETVAL -eq 0 ]; then
    echo OK
    if [ "x$CURUSER" != "x$BASE_USER" ]; then
      su -l $BASE_USER -c "touch $CURDIR/multicast_@lab.name@.lock"
    else
      touch $CURDIR/multicast_@lab.name@.lock
    fi
  else
    echo NOT OK
    RETVAL=1
  fi
  return $RETVAL
}

start() {
  cd $CURDIR
  if [ -f $CURDIR/multicast_@lab.name@.pid ]; then
    PID=`cat $CURDIR/multicast_@lab.name@.pid`
    ps -fp ${PID} &>/dev/null
    RETVAL=$?
    if [ $RETVAL -eq 0 ]; then
      echo "The service multicast_@lab.name@ is already running!"
      return $RETVAL
    else
      start_service
    fi
  else
    start_service
  fi
}
stop() {
  cd $CURDIR
  if [ -f $CURDIR/multicast_@lab.name@.pid ]; then
    PID=`cat $CURDIR/multicast_@lab.name@.pid`
    ps -fp ${PID} &>/dev/null
    RETVAL=$?
    if [ $RETVAL -eq 0 ]; then
      echo "Stopping multicast_@lab.name@ $BASE_USER Service"
      if [ "x$CURUSER" != "x$BASE_USER" ]; then
        su -l $BASE_USER -c "kill $PID"
      else
        `kill $PID`
      fi
      RETVAL=$?
      if [ $RETVAL -eq 0 ]; then
        echo OK
        rm $CURDIR/multicast_@lab.name@.lock $CURDIR/multicast_@lab.name@.pid
      else
        echo NOT OK
        RETVAL=1
      fi
      return $RETVAL
    else
      echo "The service multicast_@lab.name@ is not running!"
    fi
  else
    echo "The service multicast_@lab.name@ is not running!"
    return $RETVAL
  fi
}
restart() {
  stop
  start
}

status() {
  cd $CURDIR
  if [ -f $CURDIR/multicast_@lab.name@.pid ]
  then
    PID=`cat $CURDIR/multicast_@lab.name@.pid`
    if ps ax | grep -v grep | grep $PID > /dev/null
    then
      echo "The service multicast_@lab.name@ is running."
    else
      echo "The service multicast_@lab.name@ is not running!"
    fi
#           status $PID
  else
      echo "The service multicast_@lab.name@ is not running!"
  fi
}

case "$1" in
  start)
    start
    ;;
  stop)
    stop
    ;;
  restart)
    restart
    ;;
  status)
    status
    ;;
  *)
    echo "Usage: %s {start|stop|restart|status}\n" "$0"
    exit 1
esac

#exit $?
