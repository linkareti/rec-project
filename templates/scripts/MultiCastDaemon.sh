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
BASE_DIR="@install.dir@@deployment.subdir@/@lab.name@/multicast"
CURDIR=`pwd`
CURUSER=`id -nu`

RETVAL=0

start() {
		cd $BASE_DIR
        echo "Starting multicast_@lab.name@ $BASE_USER Service"
        if [ "x$CURUSER" != "x$BASE_USER" ]; then
            `sudo -u $BASE_USER sh StartMultiCastController.sh &>/dev/null`
        else
            `sh $BASE_DIR/StartMultiCastController.sh &>/dev/null`
        fi
        RETVAL=$?
        echo
        if [ $RETVAL -eq 0 ]; then
                if [ "x$CURUSER" != "x$BASE_USER" ]; then
                        su -l $BASE_USER -c "touch $BASE_DIR/multicast_@lab.name@.lock"
                else
                        touch $BASE_DIR/multicast_@lab.name@.lock
                fi
        else
                RETVAL=1
        fi
        return $RETVAL
}
stop() {
		cd $BASE_DIR
        if [ -f $BASE_DIR/multicast_@lab.name@.pid ]
        then
            PID=`cat $BASE_DIR/multicast_@lab.name@.pid`
            echo "Stopping multicast_@lab.name@ $BASE_USER Service"
            if [ "x$CURUSER" != "x$BASE_USER" ]; then
                su -l $BASE_USER -c "kill $PID"
            else
                `kill $PID`
            fi
            RETVAL=$?
            echo
            [ $RETVAL -eq 0 ] && rm -rf $BASE_DIR/multicast_@lab.name@.lock && rm -rf $BASE_DIR/multicast_@lab.name@.pid || \
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
		cd $BASE_DIR
        if [ -f $BASE_DIR/multicast_@lab.name@.pid ]
        then
            PID=`cat $BASE_DIR/multicast_@lab.name@.pid`
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
