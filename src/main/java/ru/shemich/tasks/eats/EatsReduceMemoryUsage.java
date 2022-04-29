package ru.shemich.tasks.eats;


import gnu.trove.map.hash.THashMap;

import java.util.Random;

/*
    Способ, который нашел - это использование сторонней оптимизированной коллекции. В данном случае Trove -> THashMap
    Возможно есть другие варианты решения задачи, но я их не нашел.
*/

public class EatsReduceMemoryUsage {

    //static final int dataSize = 18_760_327;
    static final int dataSize = 10_000_000;
    static final long targetPhoneNumber = 89253530100L;
    static long testPhoneNumber = 89000000000L;
    static final String targetFullName = "Alexey Shemyakin Aleksandrovich";
    static String testFullName = "Ivan Ivanov Ivanovich";

    public static void main(String[] args) {

        long beforeUsedMem=Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        //таймер
        long time = System.nanoTime();//
        THashMap<Long, String> data = new THashMap<>();


        //то что хотим найти в мапе
        data.put(targetPhoneNumber, targetFullName);

        //заполняем мапу муляжом
        for (int i = 0; i < dataSize - 1; i++) {
            data.put(testPhoneNumber++, testFullName + 'i');
        }

        System.out.println("Map size: " + data.size());
        System.out.println("Fetching by phone number: " + targetPhoneNumber);
        System.out.println(data.get(targetPhoneNumber));

        /*
         * таймер итог*/
        time = System.nanoTime() - time;
        System.out.printf("\nВыполнено за %,9.1f мс\n", time / 1_000_000.0);

        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long actualMemUsed = afterUsedMem - beforeUsedMem;
        String result = String.format("%.1f", actualMemUsed / Math.pow(1024,2));
        System.out.println(result + " МБайт");

        /*
        X МБайт - генерировать телефон 17s
        X МБайт - вставляем инкремент (0, 1, 2, ..., 18_760_327 - 1) 3s
        X МБайт - вставляем инкремент формата 89_ (89000000000, 89000000001, ..., 89000000002) 3s
        (вывод-на память не влияет и в реальности генерировать не надо) -> Время 3 секунды (ради интереса)

        ~0145 МБайт - 01_000_000 записей; записей/МБ = 6896; 0,3s
        ~1350 МБайт - 10_000_000 записей; записей/МБ = 7407;   2s
        ~1507 МБайт - 12_000_000 записей; записей/МБ = 7962;   2s
        ~1960 МБайт - 16_000_000 записей; записей/МБ = 8163
        ~2027 МБайт - 16_500_000 записей; записей/МБ = 8163
        ~2040 МБайт - 16_700_000 записей; записей/МБ = 8186;  16s
        k = ~8200

        Больше 16_700_000 посчитать не смог, было OutOfMemory, поэтому разделим 18_760_327 (записей) на 8200 (записей/1МБ) = 2287,8 МБ = 2,3Гб
        Ответ: 1) 2,3ГБ используя HashMap. 2) 1,9ГБ использую THashMap
        */
    }

    /**
     * @return случайный номер телефона 89.........(с дублями=>ключей меньше)
     */
    public static long generatePhoneNumber(){
        return (long) ((Math.random() * (89999999999L - 89000000000L)) + 89000000000L);
    }

    /**
     * @param nameAverageLength средняя длина полного имени
     * @return случайный номер телефона 89XXXXXXXXX(есть дубли т.е ключей меньше)
     */
    public static String generateString(int nameAverageLength) {
        int leftLimit = 97; // буква 'a'
        int rightLimit = 122; // буква 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(nameAverageLength);
        for (int i = 0; i < nameAverageLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}