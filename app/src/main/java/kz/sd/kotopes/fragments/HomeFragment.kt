package kz.sd.kotopes.fragments

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.sd.kotopes.AnimalAdapter
import kz.sd.kotopes.R
import kz.sd.kotopes.base.BaseFragment
import kz.sd.kotopes.databinding.FragmentHomeBinding
import kz.sd.kotopes.firebase.Animal

@AndroidEntryPoint
class HomeFragment:BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onBindView() {
        super.onBindView()
        var adapter = AnimalAdapter()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.submitList(getAnimals())
        adapter.itemClick = {
            findNavController().navigate(HomeFragmentDirections.actionHomeToAnimalDetailsFragment(it))
        }
        binding.editText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                if(binding.editText.text!!.isNotEmpty()){
                    adapter.submitList(searchAnimal(binding.editText.text.toString()))
                }
                else{
                    Toast.makeText(requireContext(), "Введите имя питомца", Toast.LENGTH_SHORT).show()
                }
            }

            false
        })
        binding.catChip.setOnClickListener {
            adapter.submitList(filterCats())
        }
        binding.dogChip.setOnClickListener{
            adapter.submitList(filterDogs())
        }
        binding.othersChip.setOnClickListener{
            adapter.submitList(filterOthers())
        }
    }
}

fun getAnimals():List<Animal>{
    return listOf(
        Animal(id = "111", "Джуно", "\uD83D\uDC08Джуно, мальчик ⠀ Бывший уличный «бойцовский» кот, а сейчас упитанный избалованный медвежонок\uD83E\uDD17 Джуно уже привык к приюту, другим кошкам, людям и стал очень покладистым и ласковым котиком. Джуно уже очень давно ждет своих хозяев и сильно хочет домой \uD83D\uDE4F\uD83C\uDFFB\uD83D\uDE4F\uD83C\uDFFB\uD83D\uDE4F\uD83C\uDFFB ⠀ \uD83C\uDFE1Отдается только в ответственные и заботливые руки. ⠀ \uD83D\uDCDE +7 (701) 126-66-66 \uD83D\uDCDE +7 (702) 262-39-82 ⠀ #джунокп", R.drawable.photo_1, "cat"),
        Animal(id = "112", "Лайза", "\uD83D\uDE38Лайза, девочка Посмотрите на эту очаровательную мини пантеру с зелёными глазками! Очень ласковая и нежная, она пережила предательство, и сейчас ищет заботливых родителей, чтобы наконец обрести настоящий дом. \uD83D\uDC95 Между прочим, в Великобритании чёрные кошки - это символ удачи и процветания! \uD83C\uDF40  \uD83C\uDFE1\uD83D\uDC96 \uD83C\uDFE1 +7 (701) 126-66-66 \uD83D\uDCDE +7 (702) 262-39-82 ⠀ #лайзакп", R.drawable.photo_2, "cat"),
        Animal(id = "113", "Инки", "Инки, мальчик Помните про кота, у которого была страшно изодранна щечка? Все с ним сейчас хорошо! Прошёл восстановление в приюте, получил вакцинацию и кастрацию. Отлично себя чувствует, а щечка как новая\uD83E\uDD17 \uD83C\uDFE1Отдается только в ответственные и заботливые руки. ⠀ \uD83D\uDCDE +7 (701) 126-66-66 \uD83D\uDCDE +7 (702) 262-39-82 ⠀ #инкикп", R.drawable.photo_3, "cat"),
        Animal(id = "114", "Вираж", "Вираж, мальчик Вспомним историю этого красавца? К сожалению, она была невесёлой. Вираж поступил в клинику в критическом состоянии, с инфекцией и сильным обезвоживанием. Он был очень слаб, целую неделю не ел самостоятельно. Котику было совсем плохо, но врачи, к счастью, смогли спасти его маленькую кошачью жизнь. ❤️\u200D\uD83E\uDE79 Сейчас Вираж здоров и очень хочет наконец-то найти безопасное место в этом огромном, страшном мире, которое мы, люди, зовём просто – дом. ", R.drawable.photo_4, "cat"),
        Animal(id = "115", "Ари", "Ари, девочка Штооо? Я до сих пор в приюте??! Черная красота\uD83D\uDDA4 Ари очень эффектная кошечка, пушистая, весёлая и милая \uD83E\uDD70 Ари как и все подопечные приюта мечтает скорее попасть в дом \uD83D\uDE4F\uD83C\uDFFB Присмотритесь к ней ❤️ \uD83C\uDFE1Отдается только в ответственные и заботливые руки. ⠀ \uD83D\uDCDE +7 (701) 126-66-66 \uD83D\uDCDE +7 (702) 262-39-82 ⠀ #арикп", R.drawable.photo_5, "cat"),
        Animal(id = "116", "Авалон", "Авалон, мальчик. Авалон не только красив внешне, но и благороден, гармоничен, хладнокровен. Любит тишину и уединение. Вживую, этот кот ослепительно прекрасен! Приезжайте знакомиться с нашим роскошным котом\uD83D\uDC51 \uD83C\uDFE1Отдается только в ответственные и заботливые руки. ⠀ \uD83D\uDCDE +7 (701) 126-66-66 \uD83D\uDCDE +7 (702) 262-39-82 ⠀ #авалонкп", R.drawable.photo_6, "cat"),
        Animal(id = "117", "Гринч", "Помните нашего дикого котика Гринча? Кажется, что имя ему теперь совсем не подходит, потому что, как оказалось, внутри Гринча прятался вот такой нежный и сладкий ребёнок.Отдается только в ответственные и заботливые руки. ⠀ \uD83D\uDCDE +7 (701) 126-66-66 \uD83D\uDCDE +7 (702) 262-39-82 #гринчкп", R.drawable.photo_7, "cat"),
        Animal(id = "121", "Вита", "Вита ❤ Такая она нереальная собака, если бы вы только знали, какой скрывается потенциал у нее, видео даже не передают все то что она излучает. Эта малышка сидит на балансе фонда аж с 2015 года и ей как и многим другим не посчастливилось обрести семью. \uD83D\uDE4F", R.drawable.photo_11, "dog"),
        Animal(id = "118", "Тори", "Тори, мальчик Вы только посмотрите, как он изменился! \uD83D\uDE0D Тори нашли на улице с ужасным кожным заболеванием, он отчаянно боролся за жизнь и надеялся на чудо, которое и произошло. \uD83E\uDE84 Сейчас Тори в приюте, здоровый, сытый, но всё ещё грустный. Потому что котикам, как и людям, бывает грустно, когда они чувствуют себя одиноко. А Тори очень-очень одиноко, ведь у него ещё нет своего человека.", R.drawable.photo_8, "cat"),
        Animal(id = "119", "Марфа", "Марфа, девочка Мы питаем особые тёплые чувства к нашей нежной Марфе. Или как мы ее ласково называем-Марфуше. За всю жизнь она и так настрадалась вдоволь. Возможно ее выбросили на улицу, ведь в ней есть определенно что-то британское. Она выживала на улице с больными котятами-инвалидами (которые кстати, благополучно обрели дом, несмотря на свою особенность).", R.drawable.photo_9, "cat"),
        Animal(id = "120", "Нолик", "Нолик, девочка Их с сестрёнкой выкинули, как ненужную вещь… \uD83D\uDE22 Нолик – это настоящий образец скромности. Они с сестрёнкой не очень общительны, но их можно понять – всю жизнь им приходилось переезжать с места на место и ютиться в чужом доме, ведь своего у них никогда не было.", R.drawable.photo_10, "cat"),
    )
}
fun searchAnimal(input:String):List<Animal>{
    val animals = getAnimals()
    return animals.filter { animal ->
        animal.name!!.contains(input, ignoreCase = true)
    }
}

fun filterCats():List<Animal>{
    return getAnimals().filter{ animal ->
        animal.type == "cat"
    }
}
fun filterDogs():List<Animal>{
    return getAnimals().filter { animal ->
        animal.type == "dog"
    }
}
fun filterOthers():List<Animal>{
    return getAnimals().filter{ animal ->
        animal.type != "dog" && animal.type != "cat"
    }
}