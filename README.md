# Pisano

LÜTFEN OKUYUN !!!

Proje Gauge - BDD framework kullanılarak yazılmıştır.

Senaryolara ait detayları /specs/Pisano.spec dosyasında bulabilirsiniz.
Senaryoları koşmak için Pisano.spec dosyasını kullanınız.

Elementlere ait locator’ları /resources/locators/locators.json dosyasında bulabilirsiniz.

Elements class’ı locators.json dosyasındaki elementlerin key,value ve type’ını çekmemize yarar.
Bu elementleri hashmap’e atarak key değerleri ile spec ve concept dosyasında çağırabiliriz.
Spec dosyasında element isimleri ile karışıklık yaratmamak için elementleri Stepleri kullanarak Concept.cpt dosyasında çağırdım.Detaylı olarak bakabilirsiniz.

BaseTest class’ında elementler için liste oluşturuldu. Driver için setup fonksiyonu yapıldı.
Webdriver manager kullanıldı. Bu nedenle proje içerisine herhangi bir driver yüklemeye gerek yoktur.
Çalıştırmak istediğiniz ortam ve browser ismini tanımlı stringlere girmeniz yeterlidir.

BasePage class’ında main fonksiyonları görebilirsiniz. (Findelement, Clickelement etc.)

StepImplementation sayfasında Concept.cpt dosyasında çağırılan stepleri bulabilirsiniz.
![image](https://user-images.githubusercontent.com/52383689/185947973-3d6bced5-fd40-4b59-8850-4cbbf36de1b9.png)
