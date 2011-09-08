#!/bin/bash
#
# chkconfig: 35 91 9
# description: Starts and stops the @experiment.name@ daemon \
#

BASE_USER=elab
CURDIR=`pwd`
CURUSER=`id -nu`

RETVAL=0

start() {
        echo "Starting @experiment.name@ $BASE_USER Service"
        if [ "x$CURUSER" != "x$BASE_USER" ]; then
             su -l $BASE_USER -c "nohup $CURDIR/Start@experiment.name@Driver.sh 2>&1 >/dev/null"
        else
             nohup $CURDIR/Start@experiment.name@Driver.sh 2>&1 >/dev/null
        fi
        RETVAL=$?
        echo
        [ $RETVAL -eq 0 ] && touch @experiment.name@.lock || \
           RETVAL=1
        return $RETVAL
}
stop() {
        if [ -f @experiment.name@.pid ]
        then
            PID=`cat @experiment.name@.pid`
            echo "Stopping @experiment.name@ $BASE_USER Service"
            if [ "x$CURUSER" != "x$BASE_USER" ]; then
                su -l $BASE_USER -c kill $PID
            else
                `kill $PID`
            fi
            RETVAL=$?
            echo
            [ $RETVAL -eq 0 ] && rm -rf @experiment.name@.lock && rm -rf @experiment.name@.pid || \
               RETVAL=1
            return $RETVAL
        else
            echo "The service @experiment.name@ is not running!"
            return $RETVAL
        fi
}
restart() {
        stop
        start
}
status() {
        if [ -f @experiment.name@.pid ]
        then
            PID=`cat @experiment.name@.pid`
            if ps ax | grep -v grep | grep $PID > /dev/null
            then
                echo "The service @experiment.name@ is running."
            else
                echo "The service @experiment.name@ is not running!"
            fi
#           status $PID
        else
            echo "The service @experiment.name@ is not running!"
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

exit $?
