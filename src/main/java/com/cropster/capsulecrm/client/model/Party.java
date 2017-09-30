package com.cropster.capsulecrm.client.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Wither;

@Builder(toBuilder = true)
@Wither
@Value
@JsonRootName("party")
@JsonDeserialize(builder = Party.PartyBuilder.class)
public class Party
{

    private Long id;
    private PartyType type;
    private String firstName;
    private String lastName;
    private String title;
    private String jobTitle;
    private Party organisation;
    private String name;
    private String about;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastContactedAt;
    @Singular private List<Address> addresses;
    @Singular private List<Phone> phoneNumbers;
    @Singular private List<Website> websites;
    @Singular private List<Email> emailAddresses;
    private String pictureURL;
    @Singular private List<Tag> tags;
    @Singular private List<CustomField> fields;
    private String owner;

    public Optional<Email> firstEmail()
    {
        if (emailAddresses == null || emailAddresses.isEmpty())
            return Optional.empty();
        return Optional.of(emailAddresses.get(0));
    }

    public enum PartyType
    {

        @JsonProperty("organisation") ORGANISATION,
        @JsonProperty("person") PERSON;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class PartyBuilder
    {
        public PartyBuilder deleteTag(Tag tag)
        {
            return this.tag(
                    Tag.builder()
                            .id(tag.getId())
                            .delete(true)
                            .build()
            );
        }

        public PartyBuilder deleteField(CustomField field)
        {
            return this.field(
                    CustomField.builder()
                            .id(field.getId())
                            .delete(true)
                            .build()
            );
        }
    }

    @JsonRootName("parties")
    public static class PartyList extends ArrayList<Party>
    {
    }
}
