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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.2.3
 */
public class MediaEntityJSONImpl extends EntityIndex implements MediaEntity {
	private static final long serialVersionUID = 3609683338035442290L;
	private long id;
	private String url;
	private String mediaURL;
	private String mediaURLHttps;
	private String expandedURL;
	private String displayURL;
	private Map<Integer, MediaEntity.Size> sizes;
	private String type;

	MediaEntityJSONImpl(JSONObject json) throws TwitterException {
		try {
			JSONArray indicesArray = json.getJSONArray("indices");
			setStart(indicesArray.getInt(0));
			setEnd(indicesArray.getInt(1));
			this.setId(ParseUtil.getLong("id", json));

			this.setUrl(json.getString("url"));
			this.setExpandedURL(json.getString("expanded_url"));
			this.setMediaURL(json.getString("media_url"));
			this.setMediaURLHttps(json.getString("media_url_https"));
			this.setDisplayURL(json.getString("display_url"));

			JSONObject sizes = json.getJSONObject("sizes");
			this.setSizes(new HashMap<Integer, MediaEntity.Size>(4));
			// thumbworkarounding API side issue
			addMediaEntitySizeIfNotNull(this.getSizes(), sizes, MediaEntity.Size.LARGE, "large");
			addMediaEntitySizeIfNotNull(this.getSizes(), sizes, MediaEntity.Size.MEDIUM, "medium");
			addMediaEntitySizeIfNotNull(this.getSizes(), sizes, MediaEntity.Size.SMALL, "small");
			addMediaEntitySizeIfNotNull(this.getSizes(), sizes, MediaEntity.Size.THUMB, "thumb");
			if (!json.isNull("type")) {
				this.setType(json.getString("type"));
			}

		} catch (JSONException jsone) {
			throw new TwitterException(jsone);
		}
	}

	private void addMediaEntitySizeIfNotNull(Map<Integer, MediaEntity.Size> sizes, JSONObject sizesJSON, Integer size,
			String key) throws JSONException {
		if (!sizesJSON.isNull(key)) {
			sizes.put(size, new Size(sizesJSON.getJSONObject(key)));
		}
	}

	/* For serialization purposes only. */
	public MediaEntityJSONImpl() {

	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getMediaURL() {
		return mediaURL;
	}

	@Override
	public String getMediaURLHttps() {
		return mediaURLHttps;
	}

	@Override
	public String getText() {
		return getUrl();
	}

	@Override
	public String getURL() {
		return getUrl();
	}

	@Override
	public String getDisplayURL() {
		return displayURL;
	}

	@Override
	public String getExpandedURL() {
		return expandedURL;
	}

	@Override
	public Map<Integer, MediaEntity.Size> getSizes() {
		return sizes;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public int getStart() {
		return super.getStart();
	}

	@Override
	public int getEnd() {
		return super.getEnd();
	}

	public static class Size implements MediaEntity.Size {
		private static final long serialVersionUID = -2515842281909325169L;
		private int width;
		int height;
		int resize;

		/* For serialization purposes only. */
		/* package */
		public Size() {
		}

		public Size(JSONObject json) throws JSONException {
			setWidth(json.getInt("w"));
			height = json.getInt("h");
			resize = "fit".equals(json.getString("resize")) ? MediaEntity.Size.FIT : MediaEntity.Size.CROP;
		}

		@Override
		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		@Override
		public int getHeight() {
			return height;
		}
		
		public void setHeight(int height) {
			this.height = height;
		}

		@Override
		public int getResize() {
			return resize;
		}
		
		public void setResize(int resize) {
			this.resize = resize;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (!(o instanceof Size))
				return false;

			Size size = (Size) o;

			if (height != size.height)
				return false;
			if (resize != size.resize)
				return false;
			if (getWidth() != size.getWidth())
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			int result = getWidth();
			result = 31 * result + height;
			result = 31 * result + resize;
			return result;
		}

		@Override
		public String toString() {
			return "Size{" + "width=" + getWidth() + ", height=" + height + ", resize=" + resize + '}';
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof MediaEntityJSONImpl))
			return false;

		MediaEntityJSONImpl that = (MediaEntityJSONImpl) o;

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
		return "MediaEntityJSONImpl{" + "id=" + getId() + ", url=" + getUrl() + ", mediaURL=" + getMediaURL() + ", mediaURLHttps="
				+ getMediaURLHttps() + ", expandedURL=" + getExpandedURL() + ", displayURL='" + getDisplayURL() + '\'' + ", sizes="
				+ getSizes() + ", type=" + getType() + '}';
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setMediaURL(String mediaURL) {
		this.mediaURL = mediaURL;
	}

	public void setMediaURLHttps(String mediaURLHttps) {
		this.mediaURLHttps = mediaURLHttps;
	}

	public void setExpandedURL(String expandedURL) {
		this.expandedURL = expandedURL;
	}

	public void setDisplayURL(String displayURL) {
		this.displayURL = displayURL;
	}

	public void setSizes(Map<Integer, MediaEntity.Size> sizes) {
		this.sizes = sizes;
	}

	public void setType(String type) {
		this.type = type;
	}
}
