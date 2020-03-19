#!/bin/bash
#
# description: Starts and stops the @experiment.name@ daemon \
#


EXPERIMENT_NAME=@experiment.name@
CURDIR=`pwd`
CURUSER=`id -nu`
ADDITIONAL_SERVICE_START="@additional.service.start.script@"
ADDITIONAL_SERVICE_STOP="@additional.service.stop.script@"

java_is_running() {
	[[ -f "$CURDIR/@experiment.name@.pid" ]] || return 1
	PID=`cat $CURDIR/@experiment.name@.pid`
	ps aux | grep $PID | grep java > /dev/null 2>&1
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

	if [ "x$ADDITIONAL_SERVICE_START" != "x" ]; then
		echo "Starting additional service '$ADDITIONAL_SERVICE_START'..."
		cd $CURDIR
		nohup $ADDITIONAL_SERVICE_START 2>&1 >/dev/null 2>/dev/null
		if [ $? -eq 0 ]; then
			echo "OK"
		else
			echo "Not OK"
			return 1
		fi
	fi
	
	echo "Starting @experiment.name@ service..."

	cd $CURDIR
	nohup $CURDIR/Start@experiment.name@Driver.sh 2>&1 >/dev/null 2>/dev/null

	if [ $? -eq 0 ]; then
		echo "OK"
		touch $CURDIR/@experiment.name@.lock
		return 0
	else
		echo "Not OK"
		return 1
	fi
}


stop() {
	if ( java_is_running ); then

		if [ "x$ADDITIONAL_SERVICE_STOP" != "x" ]; then
			echo "Stoping additional service '$ADDITIONAL_SERVICE_STOP'..."
			cd $CURDIR
			nohup $ADDITIONAL_SERVICE_STOP 2>&1 >/dev/null 2>/dev/null
			if [ $? -eq 0 ]; then
				echo "OK"
			else
				echo "Not OK"
				return 1
			fi
		fi


		PID=`cat $CURDIR/@experiment.name@.pid`

		echo "Stopping @experiment.name@ service..."
		kill $PID
		if [ $? -eq 0 ]; then
			[ -f $CURDIR/@experiment.name@.lock ] && rm -rf $CURDIR/@experiment.name@.lock
			[ -f $CURDIR/@experiment.name@.pid ] && rm -rf $CURDIR/@experiment.name@.pid
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
