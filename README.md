Ikke fungerende! <br>
API nøkkler er døde og nye må brukes for et fungerende produkt.

Case <br>
Som case for prosjektet valgte vi åpen case. Grunnen til at vi valgte åpen case var fordi vi simpelthen ikke 
følte at det var mye interesse for de andre casene i gruppen. Vi ville heller gjøre noe nytt og nyskapende. 
Derfor bestemte vi oss for å utvikle en app som skal bruke værdata til å generere en pakkeliste for en planlagt 
norgesferie. I appen skal brukeren kunne angi sted og tidsrom for en tur, for så å få forslag til hvilke klær 
man bør ta med seg basert på hvordan været blir på turen.

Da vi brainstormet denne ideen hadde vi en rekke ekstra funksjonalitet vi hadde lyst til å legge til. Den ekstra 
funksjonaliteten skulle gi ekstra verdi til brukeren. Blant annet skulle appen kunne gi forslag til andre 
gjenstander enn klær, f.eks. solkrem og solbriller basert på UV-indeksen. I tillegg så var det også planer om 
å legge til funksjonalitet som kunne gi brukeren beskjed om eventuelle farevarsel som er aktuelle for turen.

Løsning <br>
Vi bestemte oss for at løsningen skulle være en app som heter Cleather. Appen kan blant annet fungere som en 
pakkeliste og har støtte for lokallagring av informasjon på mobilen sånn at den kan brukes når man ikke har 
tilgang til nett. Den er også designet på en enkel og interessant måte, med farger inspirert av Meteorologisk 
Institutts værikoner.

Appen bruker per i dag fem API-er for å kunne presentere den informasjonen vi mener er viktig å få med i 
løsningen. Informasjonen om været og farevarsler er hentet fra Meteorologisk Institutts API-er, LocationForecast 
for værdata og MetAlerts for farevarsler. Google Maps for Android brukes som kartfunksjon og den statiske 
karttjenesten Geoapify’s Static Maps er brukt for å generere et kartutsnitt som brukes i grensesnittet. Big Data 
Cloud’s Geocoding API er brukt for å hente stedsnavnet gitt fra et sett koordinater.
