package com.cropster.capsulecrm.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import com.cropster.capsulecrm.client.model.Address;
import com.cropster.capsulecrm.client.model.BaseTest;
import com.cropster.capsulecrm.client.model.CustomField;
import com.cropster.capsulecrm.client.model.CustomFieldDefinition;
import com.cropster.capsulecrm.client.model.Email;
import com.cropster.capsulecrm.client.model.NestedCustomFieldDefinition;
import com.cropster.capsulecrm.client.model.Party;
import com.cropster.capsulecrm.client.model.Phone;
import com.cropster.capsulecrm.client.model.Tag;

/**
 * @author Casey Link
 * created on 2017-09-20
 */
public class ModulePartyTest extends BaseTest
{

    private Party scottSpacey()
    {
        return Party.builder()
                .id(11587L)
                .type(Party.PartyType.PERSON)
                .about("testing testing")
                .firstName("Scott")
                .lastName("Spacey")
                .jobTitle("Creative Director")
                .createdAt(Instant.parse("2015-09-15T10:43:23Z"))
                .updatedAt(Instant.parse("2015-09-15T10:43:23Z"))
                .address(Address.builder()
                        .id(12135L)
                        .city("Chicago")
                        .country("United States")
                        .street("847 North Rush Street")
                        .state("IL")
                        .zip("65629")
                        .build())
                .phoneNumber(Phone.builder()
                        .id(12133L)
                        .number("773-338-7786")
                        .build())
                .emailAddress(Email.builder()
                        .id(12134L)
                        .type("Work")
                        .address("scott@homestyleshop.co")
                        .build())
                .pictureURL(
                        "https://capsulecrm.com/theme/default/images/person_avatar_70.png")
                .build();
    }

    @Test
    public void fetchAll() throws Exception
    {
        String responseBody = TestUtils.fileToString("party_list.json");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
                .setHeader("X-Pagination-Has-More", "true")
                .setHeader("Link", "<https://api.capsulecrm.com/api/v2/parties/tags?page=2&perPage=50>; rel=\"next\"")
        );

        CArray<Party> parties = client.parties().all();

        assertThat(parties.hasNextPage()).isEqualTo(true);
        assertThat(parties.getLink()).isNotNull();

        // exercising the serialization/deserialziation settings
        assertThat(parties)
                .contains(scottSpacey())
                .contains(Party.builder()
                        .id(123L)
                        .type(Party.PartyType.ORGANISATION)
                        .name("Coffee Roaster")
                        .build())
                .contains(Party.builder()
                        .id(111L)
                        .type(Party.PartyType.ORGANISATION)
                        .tag(Tag.builder()
                                .id(555L)
                                .name("tag1")
                                .dataTag(false)
                                .build())
                        .tag(Tag.builder()
                                .id(666L)
                                .name("tag2")
                                .dataTag(true)
                                .build())
                        .field(CustomField.builder()
                                .id(1111L)
                                .value("Partner")
                                .definition(NestedCustomFieldDefinition.builder()
                                        .id(777L)
                                        .name("StringField")
                                        .build())
                                .build())
                        .field(CustomField.builder()
                                .id(666L)
                                .valueBool(true)
                                .definition(NestedCustomFieldDefinition.builder()
                                        .id(222L)
                                        .name("BoolField")
                                        .build())
                                .build())
                        .field(CustomField.builder()
                                .id(777L)
                                .valueDate(LocalDate.parse("2017-02-28"))
                                .definition(NestedCustomFieldDefinition.builder()
                                        .id(111L)
                                        .name("DateField")
                                        .build())
                                .build())

                        .build());
    }

    @Test
    public void fetchByTag() throws Exception
    {
        String responseBody = TestUtils.fileToString("party_list.json");
        server.enqueue(new MockResponse().setResponseCode(200).setBody(responseBody));

        CArray<Party> parties = client.parties()
                .ofType(Party.PartyType.ORGANISATION)
                .withTag("TAG")
                .all();

        Party.builder().build().withId(null);

        // we're not testing that the returned Party-ies have only the right tag,
        // rather, we're just exercising the querying and serialization plumbing
        assertThat(parties).isNotEmpty();
        CustomField.builder().valueBool(true);
    }

    @Test
    public void updateParty() throws InterruptedException, IOException
    {
        String responseBody = TestUtils.fileToString("party_single.json");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseBody));

        Party payload = Party.builder().id(11587L).about("testing testing").build();
        Party expectedResponse = scottSpacey();
        Party party = client.parties().update(payload).get();
        assertThat(party).isEqualTo(expectedResponse);

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/api/v2/parties/11587");
        assertThat(request.getHeader("Authorization")).isNotNull();

        Party serializedPayload = readParty(request);

        assertThat(serializedPayload).isEqualTo(payload);
    }

    @Test
    public void removeTag() throws InterruptedException, IOException
    {
        String responseBody = TestUtils.fileToString("party_single.json");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseBody));

        Tag tag = Tag.builder()
                .id(1L)
                .build();

        Party partyPayload = Party.builder()
                .id(11587L)
                .tag(tag).build();

        Party responseParty = client.parties().remove(partyPayload, tag).get();

        Party expectedResponse = scottSpacey();
        assertThat(responseParty).isEqualTo(expectedResponse);

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/api/v2/parties/11587");
        assertThat(request.getHeader("Authorization")).isNotNull();

        Party expectedRequestBody = Party.builder()
                .id(11587L)
                .tag(tag.toBuilder().delete(true).build()).build();
        Party serialized = readParty(request);
        assertThat(serialized).isEqualTo(expectedRequestBody);
    }
  @Test
    public void removeField() throws InterruptedException, IOException
    {
        String responseBody = TestUtils.fileToString("party_single.json");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseBody));

        CustomField customField = CustomField.builder()
                .id(1L)
                .build();

        Party partyPayload = Party.builder()
                .id(11587L)
                .field(customField).build();

        Party responseParty = client.parties().remove(partyPayload, customField).get();

        Party expectedResponse = scottSpacey();
        assertThat(responseParty).isEqualTo(expectedResponse);

        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/api/v2/parties/11587");
        assertThat(request.getHeader("Authorization")).isNotNull();

        Party expectedRequestBody = Party.builder()
                .id(11587L)
                .field(customField.toBuilder().delete(true).build()).build();
        Party serialized = readParty(request);
        assertThat(serialized).isEqualTo(expectedRequestBody);
    }

    private Party readParty(RecordedRequest request) throws IOException
    {
        return objectMapper.readerFor(Party.class).readValue(request.getBody().inputStream());
    }

    @Test
    public void fetchFieldDefs()
    {
        String responseBody = TestUtils.fileToString("fields_list.json");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseBody));

        CArray<CustomFieldDefinition> definitions = client.parties().definitions();
        assertThat(definitions)
                .contains(CustomFieldDefinition.builder()
                        .id(1L)
                        .type(CustomFieldDefinition.CustomFieldTypes.LIST)
                        .description("Primary language of contact")
                        .displayOrder(2)
                        .name("Language")
                        .option("DE")
                        .option("EN")
                        .option("ES")
                        .option("JP")
                        .option("KR")
                        .option("other")
                        .build())
                .contains(CustomFieldDefinition.builder()
                        .type(CustomFieldDefinition.CustomFieldTypes.DATE)
                        .id(2L)
                        .description("Account creation date")
                        .displayOrder(5)
                        .name("Start Date")
                        .captureRule("organisation")
                        .tag(Tag.builder().id(4L).name("ProductAccount").dataTag(true).build())
                        .build());
    }

    @Test
    public void fetchTags() throws InterruptedException
    {
        String responseBody = TestUtils.fileToString("tags_list.json");

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseBody));

        CArray<Tag> tags = client.parties().tags();
        assertThat(tags)
                .contains(Tag.builder()
                        .id(1L)
                        .name("TAG1")
                        .description("A tag")
                        .dataTag(false).build())
                .contains(Tag.builder()
                        .id(2L)
                        .name("TAG2")
                        .description("A second tag")
                        .dataTag(true).build());

    }

}
