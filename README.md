## Selitys tilanhallinnasta

Tilat (states) ovat muuttujia, joiden muutokset kertovat Composelle että on aika päivittää käyttöliittymä. Yleisesti tilaan liittyy joku käyttöliittymän osa esim. tekstikenttä ja teksti muunnoksesta liittyen tekstikentän arvoon. Kun tilaan tehdään muutos, esim. lisätään tekstiä tekstilaatikkoon, Compose automaattisesti päivittää tarvittavan osan käyttöliittymästä vastamaan uutta tilaa, esimerkiksi muuttamalla syötteen numeron kilometreistä maileiksi ja näyttämään tuloksen tekstinä. Jos tekstilaatikon teksti ei ole tilamuuttuja vain ainoastaan normaali muuttuja, Compose ei tiedä että uudelleenpiirtäminen pitää tapahtua. Meillähän on Reactissakin käytössä tiloja käyttäen useState-muuttujia. Tilamuuttujien käytön muita esimerkkejä ovat valintalaatikko, listan kohteet sekä latausidikaattorin näkyvyys.

### Miksi ViewModel on parempi kuin pelkkä "remember"

ViewModel on parempi vaihtoehto kuin pelkkä "remember" monessa tilanteessa, sillä ViewModel säilyttää tilansa sovelluksen konfiguraation muutosten yli, esim. näytön kääntäminen sivuttain. Jos sinulla on käyttöliittymässä vaikka tekstiä, joka tulee tilamuuttujasta, kun käännät näytön tämä tilamuuttuja asettaa itsensä takaisin oletusarvoksi. Lisäksi ViewModel erottaa tilamuuttujien toimintalogiikan käyttöliittymästä.
