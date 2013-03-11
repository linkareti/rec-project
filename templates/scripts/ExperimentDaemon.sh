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

java_is_running() {
	[[ -f "$DEPLOY_DIR/@experiment.name@.pid" ]] || return 1
	PID=`cat $DEPLOY_DIR/@experiment.name@.pid`
	ps aux | grep $PID |grep java > /dev/null 2>&1
	VAR1=$?
	kill -0 $PID > /dev/null 2>&1
	VAR2=$?
	[ $VAR1 -eq 0 ] && [ $VAR2 -eq 0 ] && return 0 || return 1
}


start() {
	if ( java_is_running ); then
		echo "The service @experiment.name@ is already running"
		return 1
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
	if ( java_is_running ); then
		PID=`cat $DEPLOY_DIR/@experiment.name@.pid`

		[ -f $DEPLOY_DIR/@experiment.name@.lock ] && rm -rf $DEPLOY_DIR/@experiment.name@.lock
		[ -f $DEPLOY_DIR/@experiment.name@.pid ] && rm -rf $DEPLOY_DIR/@experiment.name@.pid

		echo "Stopping @experiment.name@ service..."
		if [ "x$CURUSER" != "x$BASE_USER" ]; then
			su -l $BASE_USER -c "kill $PID"
		else
			kill $PID
		fi

		if [ $? -eq 0 ]; then
			echo "OK"
			return 0
		else
			echo "Not OK"
			return 1
		fi
	else
		echo "The service @experiment.name@ is not running"
		return 1
	fi
}


restart() {
	stop
	start
}


status() {
	if ( java_is_running ); then
		echo "The service @experiment.name@ is running"
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
		echo "Usage: $0 {start|stop|restart|status}"
		exit 1
esac

exit $?
