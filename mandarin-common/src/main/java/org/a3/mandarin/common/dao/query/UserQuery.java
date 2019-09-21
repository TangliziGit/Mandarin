package org.a3.mandarin.common.dao.query;

import org.a3.mandarin.common.dao.AbstractQuery;
import org.a3.mandarin.common.entity.User;

import java.time.Instant;

public class UserQuery extends AbstractQuery<User> {
    @QueryWord(column = "user_id", type = MatchType.EQUAL)
    private Integer userIdEqual;

    @QueryWord(column = "phone_number", type = MatchType.LIKE)
    private String phoneNumberLike;

    @QueryWord(column = "name", type = MatchType.LIKE)
    private String nameLike;

    @QueryWord(column = "email", type = MatchType.LIKE)
    private String emailLike;

    @QueryWord(column = "sign_up_time", type = MatchType.GREATER_THAN)
    private Instant signUpTimeAfter;

    @QueryWord(column = "sign_up_time", type = MatchType.LESS_THAN)
    private Instant signUpTimeBefore;

    public UserQuery() {}

    public void setUserIdEqual(Integer userIdEqual) {
        this.userIdEqual = userIdEqual;
    }

    public void setPhoneNumberLike(String phoneNumberLike) {
        this.phoneNumberLike = phoneNumberLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public void setEmailLike(String emailLike) {
        this.emailLike = emailLike;
    }

    public void setSignUpTimeAfter(Instant signUpTimeAfter) {
        this.signUpTimeAfter = signUpTimeAfter;
    }

    public void setSignUpTimeBefore(Instant signUpTimeBefore) {
        this.signUpTimeBefore = signUpTimeBefore;
    }
}
