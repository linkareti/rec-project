#!/bin/bash
#
# chkconfig: 35 91 9
# description: Starts and stops the multicast_@lab.name@ daemon \
#

BASE_USER=elab

RETVAL=0

start() {
        echo "Starting multicast_@lab.name@ $BASE_USER Service"
#         su -l $BASE_USER -c "nohup ./StartMultiCastController.sh &"
        `sh StartMultiCastController.sh &>/dev/null`
        RETVAL=$?
        echo
        [ $RETVAL -eq 0 ] && touch multicast_@lab.name@.lock || \
           RETVAL=1
        return $RETVAL
}
stop() {
        if [ -f multicast_@lab.name@.pid ]
        then
            PID=`cat multicast_@lab.name@.pid`
            echo "Stopping multicast_@lab.name@ $BASE_USER Service"
#             su -l $BASE_USER -c kill $PID
            `kill $PID`
            RETVAL=$?
            echo
            [ $RETVAL -eq 0 ] && rm -rf multicast_@lab.name@.lock && rm -rf multicast_@lab.name@.pid || \
               RETVAL=1
            return $RETVAL
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
        if [ -f multicast_@lab.name@.pid ]
        then
            PID=`cat multicast_@lab.name@.pid`
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

exit $?
