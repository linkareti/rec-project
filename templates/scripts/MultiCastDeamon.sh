#!/bin/bash
#
# chkconfig: 35 91 9
# description: Starts and stops the multicast_@lab.name@ daemon \
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
        gprintf "Starting multicast_@lab.name@ $BASE_USER Service"
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
            gprintf "Stopping multicast_@lab.name@ $BASE_USER Service"
#             su -l $BASE_USER -c kill $PID
            `kill $PID`
            RETVAL=$?
            echo
            [ $RETVAL -eq 0 ] && rm -rf multicast_@lab.name@.lock && rm -rf multicast_@lab.name@.pid || \
               RETVAL=1
            return $RETVAL
        else
            gprintf "The service multicast_@lab.name@ is not running!\n"
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
                gprintf "The service multicast_@lab.name@ is running.\n"
            else
                gprintf "The service multicast_@lab.name@ is not running!\n"
            fi
#           status $PID
        else
            gprintf "The service multicast_@lab.name@ is not running!\n"
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
        gprintf "Usage: %s {start|stop|restart|status}\n" "$0"
        exit 1
esac

exit $?
