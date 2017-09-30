package com.cropster.capsulecrm.client;

import java.util.Map;
import java.util.Optional;

import com.cropster.capsulecrm.client.model.CustomField;
import com.cropster.capsulecrm.client.model.CustomFieldDefinition;
import com.cropster.capsulecrm.client.model.Party;
import com.cropster.capsulecrm.client.model.Tag;

/**
 * @author Casey Link
 * created on 2017-10-02
 */
public interface ModuleParty
{

    ModuleParty ofType(Party.PartyType partyType);

    ModuleParty withTag(String tag);

    CArray<Party> all();

    CArray<Party> all(Map<String, String> query);

    CArray<Party> nextPage(CArray<Party> prevPage);

    Optional<Party> one(Long id);

    Optional<Party> update(Party party);

    CArray<CustomFieldDefinition> definitions();

    Optional<CustomFieldDefinition> definition(long id);

    CArray<Tag> tags();

    Optional<Tag> tag(long id);

    Optional<Party> remove(Party party, Tag tag);

    Optional<Party> remove(Party party, CustomField field);

    Optional<Party> add(Party party, Tag tag);

    Optional<Party> add(Party party, CustomField field);
}
