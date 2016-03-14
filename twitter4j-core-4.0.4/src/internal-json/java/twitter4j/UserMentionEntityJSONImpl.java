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

/**
 * A data interface representing one single user mention entity.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.9
 */
public class UserMentionEntityJSONImpl extends EntityIndex implements UserMentionEntity {
    private static final long serialVersionUID = 6060510953676673013L;
    private String name;
    private String screenName;
    private long id;

    public UserMentionEntityJSONImpl(JSONObject json) throws TwitterException {
        super();
        init(json);
    }

    public UserMentionEntityJSONImpl(int start, int end, String name, String screenName, long id) {
        super();
        setStart(start);
        setEnd(end);
        this.setName(name);
        this.setScreenName(screenName);
        this.setId(id);
    }

    /* For serialization purposes only. */
    public UserMentionEntityJSONImpl() {

    }

    private void init(JSONObject json) throws TwitterException {
        try {
            JSONArray indicesArray = json.getJSONArray("indices");
            setStart(indicesArray.getInt(0));
            setEnd(indicesArray.getInt(1));

            if (!json.isNull("name")) {
                this.setName(json.getString("name"));
            }
            if (!json.isNull("screen_name")) {
                this.setScreenName(json.getString("screen_name"));
            }
            setId(ParseUtil.getLong("id", json));
        } catch (JSONException jsone) {
            throw new TwitterException(jsone);
        }
    }

    @Override
    public String getText() {
        return getScreenName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int getStart() {
        return super.getStart();
    }

    @Override
    public int getEnd() {
        return super.getEnd();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMentionEntityJSONImpl that = (UserMentionEntityJSONImpl) o;

        if (getId() != that.getId()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getScreenName() != null ? !getScreenName().equals(that.getScreenName()) : that.getScreenName() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getScreenName() != null ? getScreenName().hashCode() : 0);
        result = 31 * result + (int) (getId() ^ (getId() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "UserMentionEntityJSONImpl{" +
                "name='" + getName() + '\'' +
                ", screenName='" + getScreenName() + '\'' +
                ", id=" + getId() +
                '}';
    }

	public void setName(String name) {
		this.name = name;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public void setId(long id) {
		this.id = id;
	}
}
