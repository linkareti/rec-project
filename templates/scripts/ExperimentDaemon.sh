#!/bin/bash
#
# chkconfig: 35 91 9
# description: Starts and stops the @experiment.name@ daemon \
#


BASE_USER=elab
BASE_USER_HOMEDIR=~elab
EXPERIMENT_NAME=@experiment.name@
DEPLOY_DIR="$BASE_USER_HOMEDIR/rec-deployment/$EXPERIMENT_NAME"
CURDIR=`pwd`
CURUSER=`id -nu`

RETVAL=0

start() {
        echo "Starting @experiment.name@ $BASE_USER Service"
        cd $DEPLOY_DIR
        if [ "x$CURUSER" != "x$BASE_USER" ]; then
             su -l $BASE_USER -c "nohup $DEPLOY_DIR/Start@experiment.name@Driver.sh 2>&1 >/dev/null"
        else
             nohup $DEPLOY_DIR/Start@experiment.name@Driver.sh 2>&1 >/dev/null
        fi
        RETVAL=$?
        echo
        if [ $RETVAL -eq 0 ]; then
                if [ "x$CURUSER" != "x$BASE_USER" ]; then
                        su -l $BASE_USER -c "touch $DEPLOY_DIR/@experiment.name@.lock"
                else
                        touch $DEPLOY_DIR/@experiment.name@.lock
                fi
        else
                RETVAL=1
        fi
        return $RETVAL
}
stop() {
        if [ -f $DEPLOY_DIR/@experiment.name@.pid ]
        then
            PID=`cat $DEPLOY_DIR/@experiment.name@.pid`
            echo "Stopping @experiment.name@ $BASE_USER Service"
            if [ "x$CURUSER" != "x$BASE_USER" ]; then
                su -l $BASE_USER -c "kill $PID"
            else
                `kill $PID`
            fi
            RETVAL=$?
            echo
            [ $RETVAL -eq 0 ] && rm -rf $DEPLOY_DIR/@experiment.name@.lock && rm -rf $DEPLOY_DIR/@experiment.name@.pid || \
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
        if [ -f $DEPLOY_DIR/@experiment.name@.pid ]
        then
            PID=`cat $DEPLOY_DIR/@experiment.name@.pid`
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
