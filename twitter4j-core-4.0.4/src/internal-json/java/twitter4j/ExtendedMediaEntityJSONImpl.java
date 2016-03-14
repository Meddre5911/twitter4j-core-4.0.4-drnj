/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package twitter4j;

import java.util.Arrays;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 4.x.x
 */
public class ExtendedMediaEntityJSONImpl extends MediaEntityJSONImpl implements ExtendedMediaEntity {
	private static final long serialVersionUID = -3889082303259253211L;
	private int videoAspectRatioWidth;
	private int videoAspectRatioHeight;
	private long videoDurationMillis;
	private Variant[] videoVariants;

	public ExtendedMediaEntityJSONImpl(JSONObject json) throws TwitterException {

		super(json);

		try {
			if (json.has("video_info")) {
				JSONObject videoInfo = json.getJSONObject("video_info");
				JSONArray aspectRatio = videoInfo.getJSONArray("aspect_ratio");
				this.setVideoAspectRatioWidth(aspectRatio.getInt(0));
				this.setVideoAspectRatioHeight(aspectRatio.getInt(1));

				// not in animated_gif
				if (!videoInfo.isNull("duration_millis")) {
					this.setVideoDurationMillis(videoInfo.getLong("duration_millis"));
				}

				JSONArray variants = videoInfo.getJSONArray("variants");
				this.setVideoVariants(new Variant[variants.length()]);
				for (int i = 0; i < variants.length(); i++) {
					this.getVideoVariants()[i] = new Variant(variants.getJSONObject(i));
				}
			} else {
				this.setVideoVariants(new Variant[0]);
			}

		} catch (JSONException jsone) {
			throw new TwitterException(jsone);
		}
	}

	/* For serialization purposes only. */
	public ExtendedMediaEntityJSONImpl() {

	}

	@Override
	public int getVideoAspectRatioWidth() {
		return videoAspectRatioWidth;
	}

	@Override
	public int getVideoAspectRatioHeight() {
		return videoAspectRatioHeight;
	}

	@Override
	public long getVideoDurationMillis() {
		return videoDurationMillis;
	}

	@Override
	public ExtendedMediaEntity.Variant[] getVideoVariants() {
		return videoVariants;
	}

	public static class Variant implements ExtendedMediaEntity.Variant {
		private static final long serialVersionUID = 1027236588556797980L;
		int bitrate;
		String contentType;
		String url;

		public Variant(JSONObject json) throws JSONException {
			setBitrate(json.has("bitrate") ? json.getInt("bitrate") : 0);
			setContentType(json.getString("content_type"));
			setUrl(json.getString("url"));
		}

		/* For serialization purposes only. */
		public Variant() {
		}

		@Override
		public int getBitrate() {
			return bitrate;
		}

		@Override
		public String getContentType() {
			return contentType;
		}

		@Override
		public String getUrl() {
			return url;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (!(o instanceof Variant))
				return false;

			Variant variant = (Variant) o;

			if (getBitrate() != variant.getBitrate())
				return false;
			if (!getContentType().equals(variant.getContentType()))
				return false;
			if (!getUrl().equals(variant.getUrl()))
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			int result = getBitrate();
			result = 31 * result + (getContentType() != null ? getContentType().hashCode() : 0);
			result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return "Variant{" + "bitrate=" + getBitrate() + ", contentType=" + getContentType() + ", url=" + getUrl()
					+ '}';
		}

		public void setBitrate(int bitrate) {
			this.bitrate = bitrate;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ExtendedMediaEntityJSONImpl))
			return false;

		ExtendedMediaEntityJSONImpl that = (ExtendedMediaEntityJSONImpl) o;

		if (getId() != that.getId())
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (getId() ^ (getId() >>> 32));
	}

	@Override
	public String toString() {
		return "ExtendedMediaEntityJSONImpl{" + "id=" + getId() + ", url=" + getUrl() + ", mediaURL=" + getMediaURL()
				+ ", mediaURLHttps=" + getMediaURLHttps() + ", expandedURL=" + getExpandedURL() + ", displayURL='"
				+ getDisplayURL() + '\'' + ", sizes=" + getSizes() + ", type=" + getType() + ", videoAspectRatioWidth="
				+ getVideoAspectRatioWidth() + ", videoAspectRatioHeight=" + getVideoAspectRatioHeight()
				+ ", videoDurationMillis=" + getVideoDurationMillis() + ", videoVariants=" + Arrays.toString(getVideoVariants())
				+ '}';
	}

	public void setVideoAspectRatioWidth(int videoAspectRatioWidth) {
		this.videoAspectRatioWidth = videoAspectRatioWidth;
	}

	public void setVideoAspectRatioHeight(int videoAspectRatioHeight) {
		this.videoAspectRatioHeight = videoAspectRatioHeight;
	}

	public void setVideoDurationMillis(long videoDurationMillis) {
		this.videoDurationMillis = videoDurationMillis;
	}

	public void setVideoVariants(Variant[] videoVariants) {
		this.videoVariants = videoVariants;
	}
}
