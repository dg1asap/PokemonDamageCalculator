import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class PokemonDamageCalculator {
    public String calculateDamageRateAgainstType(String damageType, String ... defTypes) throws IllegalArgumentException {
        HashMap<String, DamageRelationType> damageRelations;
        try {
            damageRelations = getDamageRelationsFromDamageType(damageType);
        } catch (UnirestException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("DamageType or defTypes are wrong, can't calculate damage type");
        }
        double damageRate = calculateDamageRelationsAgainst(damageRelations, defTypes);
        return damageRate + "x";
    }

    private HashMap<String, DamageRelationType> getDamageRelationsFromDamageType(String damageType) throws UnirestException {
        JSONObject damageRelationJSON = getDamageRelationJSONForDamageType(damageType);
        return getDamageRelationsFromJSON(damageRelationJSON);
    }

    private double calculateDamageRelationsAgainst(HashMap<String, DamageRelationType> damageRelations, String ... defTypes) {
        return damageRelations.keySet().stream()
                .filter(damageRelation -> Arrays.asList(defTypes).contains(damageRelation))
                .mapToDouble(s -> damageRelations.get(s).getRatio())
                .reduce(1.0, (a, b) -> a * b);
    }

    private JSONObject getDamageRelationJSONForDamageType(String damageType) throws UnirestException {
        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("https://pokeapi.co/api/v2/type/" + damageType)
                .asJson();
        JSONObject myObj = jsonResponse.getBody().getObject();
        return myObj.getJSONObject("damage_relations");
    }

    private HashMap<String, DamageRelationType> getDamageRelationsFromJSON(JSONObject damageRelationJSON) {
        HashMap<String, DamageRelationType> doubleDamageRelation =
                getDamageRelationFromJSONObject(DamageRelationType.DOUBLE_DAMAGE_TO, damageRelationJSON);
        HashMap<String, DamageRelationType> halfDamageRelation =
                getDamageRelationFromJSONObject(DamageRelationType.HALF_DAMAGE_TO, damageRelationJSON);
        HashMap<String, DamageRelationType> noDamageRelation =
                getDamageRelationFromJSONObject(DamageRelationType.NO_DAMAGE_TO, damageRelationJSON);

        HashMap<String, DamageRelationType> damageRelations = new HashMap<>();
        damageRelations.putAll(doubleDamageRelation);
        damageRelations.putAll(halfDamageRelation);
        damageRelations.putAll(noDamageRelation);

        return damageRelations;
    }

    private HashMap<String, DamageRelationType> getDamageRelationFromJSONObject(DamageRelationType type, JSONObject jsonObject) {
        String jsonKey = type.toString();
        JSONArray jsonArray = jsonObject.getJSONArray(jsonKey);
        return getDamageRelationFromJSONArray(type, jsonArray);
    }

    private HashMap<String, DamageRelationType> getDamageRelationFromJSONArray(DamageRelationType type, JSONArray array) {
        HashMap<String, DamageRelationType> damageRelation = new HashMap<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject damageNameAsJson = (JSONObject) array.get(i);
            String damageName = (String) damageNameAsJson.get("name");
            damageRelation.put(damageName, type);
        }
        return damageRelation;
    }

}
