package com.linkare.rec.impl.newface.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * XML Type for video configuration.
 * 
 * @author bcatarino
 */
@XmlType
public class MediaConfig extends DisplayNode {

	private String videoLocation;

	private boolean canSave;

	private boolean offlinePlay;

	public MediaConfig() {
		super();
	}

	@XmlAttribute
	public boolean isCanSave() {
		return canSave;
	}

	public void setCanSave(final boolean canSave) {
		this.canSave = canSave;
	}

	@XmlAttribute
	public boolean isOfflinePlay() {
		return offlinePlay;
	}

	public void setOfflinePlay(final boolean offlinePlay) {
		this.offlinePlay = offlinePlay;
	}

	@XmlAttribute
	public String getVideoLocation() {
		return videoLocation;
	}

	public void setVideoLocation(final String videoLocation) {
		this.videoLocation = videoLocation;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MediaConfig other = (MediaConfig) obj;
		if ((videoLocation == null) ? (other.videoLocation != null) : !videoLocation.equals(other.videoLocation)) {
			return false;
		}
		if (canSave != other.canSave) {
			return false;
		}
		if (offlinePlay != other.offlinePlay) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 79 * hash + (videoLocation != null ? videoLocation.hashCode() : 0);
		hash = 79 * hash + (canSave ? 1 : 0);
		hash = 79 * hash + (offlinePlay ? 1 : 0);
		return hash;
	}

}
