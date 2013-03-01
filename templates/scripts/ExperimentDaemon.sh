#!/bin/bash
#
# description: Starts and stops the @experiment.name@ daemon \
#


BASE_USER=elab
BASE_USER_HOMEDIR=~elab
EXPERIMENT_NAME=@experiment.name@
DEPLOY_DIR="$BASE_USER_HOMEDIR/rec-deployment/$EXPERIMENT_NAME"
CURDIR=`pwd`
CURUSER=`id -nu`

start() {
        if [ -f $DEPLOY_DIR/@experiment.name@.pid ]; then
             PID=`cat $DEPLOY_DIR/@experiment.name@.pid`
             if kill -0 $PID > /dev/null 2>&1; then
                  echo "The service @experiment.name@ is already running"
                  return 1
             fi
        fi

        echo "Starting @experiment.name@ service..."

        if [ "x$CURUSER" != "x$BASE_USER" ]; then
             su -l $BASE_USER -c "cd $DEPLOY_DIR; nohup $DEPLOY_DIR/Start@experiment.name@Driver.sh 2>&1 >/dev/null 2>/dev/null"
        else
             cd $DEPLOY_DIR
             nohup $DEPLOY_DIR/Start@experiment.name@Driver.sh 2>&1 >/dev/null 2>/dev/null
        fi

        if [ $? -eq 0 ]; then
                echo "OK"
                if [ "x$CURUSER" != "x$BASE_USER" ]; then
                        su -l $BASE_USER -c "touch $DEPLOY_DIR/@experiment.name@.lock"
                else
                        touch $DEPLOY_DIR/@experiment.name@.lock
                fi
                return 0
        else
                echo "Not OK"
                return 1
        fi
}

stop() {
        if [ -f $DEPLOY_DIR/@experiment.name@.pid ]; then
            PID=`cat $DEPLOY_DIR/@experiment.name@.pid`

            [ -f $DEPLOY_DIR/@experiment.name@.lock ] && rm -rf $DEPLOY_DIR/@experiment.name@.lock
            [ -f $DEPLOY_DIR/@experiment.name@.pid ] && rm -rf $DEPLOY_DIR/@experiment.name@.pid

            if kill -0 $PID > /dev/null 2>&1; then
                echo "Stopping @experiment.name@ service..."
                if [ "x$CURUSER" != "x$BASE_USER" ]; then
                    su -l $BASE_USER -c "kill $PID"
                else
                    kill $PID
                fi
                [ $? -eq 0 ] && echo "OK" || echo "Not OK"
            else
                echo "The service @experiment.name@ is not running"
                return 1
            fi
        else
            echo "The service @experiment.name@ is not running"
            return 1
        fi

	return 0
}

restart() {
        stop
        start
}

status() {
        if [ -f $DEPLOY_DIR/@experiment.name@.pid ]; then

            PID=`cat $DEPLOY_DIR/@experiment.name@.pid`

            if kill -0 $PID > /dev/null 2>&1; then
                echo "The service @experiment.name@ is running"
            else
                echo "The service @experiment.name@ is not running"
            fi

        else
            echo "The service @experiment.name@ is not running"
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
