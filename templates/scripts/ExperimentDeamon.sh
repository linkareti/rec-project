#!/bin/bash
#
# chkconfig: 35 91 9
# description: Starts and stops the @experiment.name@ daemon \
#

# Source function library.
if [ -f /etc/init.d/functions ] ; then
  . /etc/init.d/functions
elif [ -f /etc/rc.d/init.d/functions ] ; then
  . /etc/rc.d/init.d/functions
else
  exit 0
fi

BASE_USER=elab

RETVAL=0

start() {
        gprintf "Starting @experiment.name@ $BASE_USER Service"
#         su -l $BASE_USER -c "nohup ./Start@experiment.name@Driver.sh &"
        `sh Start@experiment.name@Driver.sh &>/dev/null`
        RETVAL=$?
        echo
        [ $RETVAL -eq 0 ] && touch @experiment.name@.lock || \
           RETVAL=1
        return $RETVAL
}
stop() {
        PID=`cat @experiment.name@.pid`
        gprintf "Stoping @experiment.name@ $BASE_USER Service"
#         su -l $BASE_USER -c kill $PID
        `kill $PID`
        RETVAL=$?
        echo
        [ $RETVAL -eq 0 ] && rm -rf @experiment.name@.lock && rm -rf @experiment.name@.pid || \
           RETVAL=1
        return $RETVAL
}
restart() {
        stop
        start
}
status() {
        PID_FILE=`cat @experiment.name@.pid`
        status $PID
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
        gprintf "Usage: %s {start|stop|restart|status}\n" "$0"
        exit 1
esac

exit $?
