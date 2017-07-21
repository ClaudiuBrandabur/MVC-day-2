package ro.teamnet.zth.api.em;

/*
* Clasa de tip Enum care contine cele 4 tipuri de interogari
* pe care le putem face asupra unei tabele: select, insert,
* update si delete.
* Aceasta clasa va fi folosita in metoda createQuery din clasa QueryBuilder
* */
public enum QueryType {
    SELECT,
    INSERT,
    UPDATE,
    DELETE
}
