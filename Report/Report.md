# Лабораторная работа №5. UI Tests.
## Цели
  - Ознакомиться с принципами и получить практические навыки разработки UI тестов для Android приложений
## Программа работы
#### Задача 1. Простейший UI тест
Разработайте приложение, в котором есть одна кнопка (Button) и одно текстовое поле (EditText). При (первом) нажатии на кнопку текст на кнопке должен меняться. Напишите Espresso тест, который проверяет, что при повороте экрана содержимое текстового поля (каким бы оно ни было) сохраняется, а надпись на кнопке сбрасывается в исходное состояние.

Согласно заданию было создано приложение с необходимыми элементами и проведено "ручное" тестирование его функциональности. Представленные ниже скриншоты демонстрируют поведение приложения при вводе текста, нажатии на кнопку и повороте экрана.

![alt text](1.png)
![alt text](2.png)
![alt text](3.png)

После изучения материалов по работе с библотекой были написан тест, предственный ниже:

```
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun firstTest() {
        onView(withId(R.id.edit_text)).perform(typeText("Immortal string"))
        onView(withId(R.id.button)).perform(click())

        onView(withId(R.id.edit_text)).check(matches(withText("Immortal string")))
        onView(withId(R.id.button)).check(matches(withText("Button pressed")))

        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.button)).check(matches(withText("Button")))
        onView(withId(R.id.edit_text)).check(matches(withText("Immortal string")))
    }
}
```

Средствами библиотеки заполняется поле EditText и выполняется нажатие кнопки, после чего производится проверка содержимого обоих элементов. Затем, используя код из задания к лабораторной была изменена ориентация устройства и повторно проведены сравнения содержимого элементов с ожидаемыми значениями. Как и предполагалось, поворот экрана вернул текст кнопки в исходное состояние, но не удалил значение, введенное в текстовое поле. 

___

#### Задача 2. Тестирование навигации
Возьмите приложение из Лаб №3 о навигации (любое из решений). Напишите UI тесты, проверяющие навигацию между 4мя исходными Activity/Fragment (1-2-3-About). 

Для тестирования был выбран первый вариант реализации графа навигации между экранами приложения (с использованием startAcivityForResult). После этого был написан ряд тестов и вспомогательных функций. Последние направлены на проверку текущего экрана путем вызова метода isDisplayed к элементам, расположеным только на конкретном экране, а также перехода в About. Эти функции используются во всех тестах.
```
    private fun isFirst() {
        onView(withId(R.id.to_2_button)).check(matches(isDisplayed()))
    }

    private fun isSecond() {
        onView(withId(R.id.to_1_button)).check(matches(isDisplayed()))
        onView(withId(R.id.to_3_button)).check(matches(isDisplayed()))
    }

    private fun isThird() {
        onView(withId(R.id.to_1_from_3)).check(matches(isDisplayed()))
        onView(withId(R.id.to_2_from_3)).check(matches(isDisplayed()))
    }

    private fun isAbout() {
        onView(withId(R.id.about)).check(matches(isDisplayed()))
    }

    private fun toAbout() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText(R.string.to_about)).perform(click())
        isAbout()
    }
```

Набор тестов нацелен на проверку перехода между Activity путем нажатия соответствующих кнопок.
```
    @Test
    fun firstToSecond() {
        isFirst()
        onView(withId(R.id.to_2_button)).perform(click())
        isSecond()
    }

    @Test
    fun secondToThird() {
        firstToSecond()
        onView(withId(R.id.to_3_button)).perform(click())
        isThird()
    }

    @Test
    fun thirdToSecond() {
        secondToThird()
        onView(withId(R.id.to_2_from_3)).perform(click())
        isSecond()
    }

    @Test
    fun thirdToFirst() {
        secondToThird()
        onView(withId(R.id.to_1_from_3)).perform(click())
        isFirst()
    }

```

Плюс написан еще один тест, осуществляющий последовательный перход из всех 3 активити в окно About с целью проверки работоспособности Options menu на каждом экране приложения.
```
@Test
    fun toAboutTransition() {
        for (i in 1..3) {
            when (i) {
                1 -> isFirst()
                2 -> firstToSecond()
                3 -> secondToThird()
            }

            toAbout()

            when (i) {
                1 -> {
                    pressBack()
                    isFirst()
                }
                2 -> {
                    pressBack()
                    isSecond()
                    pressBack()
                }
                3 -> {
                    pressBack()
                    isThird()
                }
            }
        }
    }
```

Последние 3 теста сделаны для проверки глубины бэкстэка, то есть при нажатии кнопки назад из третьей активити мы должны попасть в первую, а из второй (не зависимо от того, из первой активити мы в нее перешли, или из третьей) - в первую. Третий тест совершает серию преходов между активити приложения, заканчивающуюся на переходе в About, после чего производится проверка, что после 4 нажать кнопки Back (максимально допустимая глубина стека) будет зафиксирован выход из приложения.
```
    @Test
    fun backFromSecond() {
        firstToSecond()
        pressBack()
        isFirst()

        thirdToSecond()
        pressBack()
        isFirst()
    }

    @Test
    fun backFromThird() {
        secondToThird()
        pressBack()
        isSecond()
        pressBack()
        isFirst()
    }

    @Test
    fun backStackValueTest() {
        var exception = false

        thirdToFirst()  //  1 -> 2 -> 3 -> 2 -> 1
        secondToFirst() //  1 -> 2 -> 1
        secondToThird() //  1 -> 2 -> 3
        toAbout()
        pressBack()
        pressBack()
        pressBack()

        try {
            pressBack()
        } catch (e: NoActivityResumedException) {
            exception = true
        }

        assertTrue(exception)
    }
```

## Вывод
В ходе выполнения данной лабораторной работы:
   - Изучена библиотека для проведения UI тестов
   - Проведено управление параметрамми устройства и отдельных элементов UI средствами библиотеки
   - Выполнено тестирование изменения состояния элементов при повороте экрана
   - Написаны тесты для реализованного в третьей лабораторной работе графа нафигации
