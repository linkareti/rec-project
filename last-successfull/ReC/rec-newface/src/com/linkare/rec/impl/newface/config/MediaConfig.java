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
	}

	@XmlAttribute
	public boolean isCanSave() {
		return canSave;
	}

	public void setCanSave(boolean canSave) {
		this.canSave = canSave;
	}

	@XmlAttribute
	public boolean isOfflinePlay() {
		return offlinePlay;
	}

	public void setOfflinePlay(boolean offlinePlay) {
		this.offlinePlay = offlinePlay;
	}

	@XmlAttribute
	public String getVideoLocation() {
		return videoLocation;
	}

	public void setVideoLocation(String videoLocation) {
		this.videoLocation = videoLocation;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MediaConfig other = (MediaConfig) obj;
		if ((this.videoLocation == null) ? (other.videoLocation != null) : !this.videoLocation.equals(other.videoLocation)) {
			return false;
		}
		if (this.canSave != other.canSave) {
			return false;
		}
		if (this.offlinePlay != other.offlinePlay) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 79 * hash + (this.videoLocation != null ? this.videoLocation.hashCode() : 0);
		hash = 79 * hash + (this.canSave ? 1 : 0);
		hash = 79 * hash + (this.offlinePlay ? 1 : 0);
		return hash;
	}

}
