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
 * A data class representing one single Hashtag entity.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.9
 */
public class HashtagEntityJSONImpl extends EntityIndex implements HashtagEntity, SymbolEntity {
    private static final long serialVersionUID = -5317828991902848906L;
    private String text;


    public HashtagEntityJSONImpl(JSONObject json) throws TwitterException {
        super();
        init(json);
    }

    public HashtagEntityJSONImpl(int start, int end, String text) {
        super();
        setStart(start);
        setEnd(end);
        this.setText(text);
    }

    /* For serialization purposes only. */
    public HashtagEntityJSONImpl() {

    }

    private void init(JSONObject json) throws TwitterException {
        try {
            JSONArray indicesArray = json.getJSONArray("indices");
            setStart(indicesArray.getInt(0));
            setEnd(indicesArray.getInt(1));

            if (!json.isNull("text")) {
                this.setText(json.getString("text"));
            }
        } catch (JSONException jsone) {
            throw new TwitterException(jsone);
        }
    }

    @Override
    public String getText() {
        return text;
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

        HashtagEntityJSONImpl that = (HashtagEntityJSONImpl) o;

        if (getText() != null ? !getText().equals(that.getText()) : that.getText() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getText() != null ? getText().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "HashtagEntityJSONImpl{" +
                "text='" + getText() + '\'' +
                '}';
    }

	public void setText(String text) {
		this.text = text;
	}
}