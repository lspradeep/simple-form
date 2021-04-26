# simple-form

##### Add this in your project's build.gradle (project level)
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

##### Add this in your project's build.gradle (app level)
```gradle
	dependencies {
	        implementation 'com.github.lspradeep:simple-form:v1.0.1'
	}
```
## Table of content
### 1. How to create a simple form
### 2. How to create a sectioned form
### 3. How to create a sectioned form and show only one section at a time
<br/><br/><br/>


# 1. How to create a simple form

|               |               | 
| ------------- | ------------- |
| ![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/simple1.png)  | ![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/simple2.png)|

### Step 1:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.pradeep.form.simple_form.SimpleFormView
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

### Step 2: (just describe your form item)
```kotlin
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.form_items.NumberInputType
import com.pradeep.form.simple_form.form_items.SingleLineTextType
import com.pradeep.form.simple_form.model.Form

    fun getFormData(): List<Form> {
        val forms = mutableListOf<Form>()
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Name",
                hint = "please enter your name",
                singleLineTextType = SingleLineTextType.TEXT,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Email",
                hint = "please enter your Email address",
                singleLineTextType = SingleLineTextType.EMAIL_ADDRESS,
                errorMessage = "Please provide a valid email address"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.MULTI_LINE_TEXT,
                question = "Bio",
                description = "tell us about you",
                hint = "I'm from India. I love ice cream.",
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_CHOICE,
                question = "Gender",
                hint = "please choose your gender",
                choices = listOf(
                    "Male",
                    "Female",
                    "Others"
                ),
                errorMessage = "Please select an answer"
            )
        )

        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Do you earn or still studying",
                description = "if yes, care to tell us how much you earn per year?",
                numberInputType = NumberInputType.NUMBER,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "What's  your score in college",
                numberInputType = NumberInputType.DECIMAL_NUMBER,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Phone number",
                hint = "please provide your phone number",
                numberInputType = NumberInputType.PHONE_NUMBER,
                errorMessage = "Please provide a valid phone number"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.MULTI_CHOICE,
                question = "Your favourite TV shows",
                description = "you can pick more than one",
                hint = "hint 3",
                choices = listOf(
                    "friends",
                    "tom and jerry",
                    "pokemon",
                    "rick and morty"
                ),
                errorMessage = "Please choose an answer"
            )
        )
        return forms
    }
```

### Step 3: 
```kotlin
binding.simpleForm.setData(getFormData(), callback = this)
```

### Step 4: 
```kotlin
implement FormSubmitCallback in your activity and override the following function 

    override fun onFormSubmitted(forms: List<Form>) {
         //do something with the result
    }
```

<br/><br/>

# 2. How to create a sectioned form

|               |               |               | 
| ------------- | ------------- | ------------- |
| ![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/sectioned1.png)  | ![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/sectioned2.png)|![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/sectioned3.png)|


### Step 1:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.pradeep.form.simple_form.SimpleFormView
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

### Step 2: (just describe your form item)
```kotlin
import com.pradeep.form.simple_form.form_items.FormTypes
import com.pradeep.form.simple_form.form_items.NumberInputType
import com.pradeep.form.simple_form.form_items.SingleLineTextType
import com.pradeep.form.simple_form.model.Form

fun getSection1FormData(): List<Form> {
      val forms = mutableListOf<Form>()
      forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Name",
                hint = "please enter your name",
                singleLineTextType = SingleLineTextType.TEXT,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "Email",
                hint = "please enter your Email address",
                singleLineTextType = SingleLineTextType.EMAIL_ADDRESS,
                errorMessage = "Please provide a valid email address"
            )
        )
	forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Phone number",
                hint = "please provide your phone number",
                numberInputType = NumberInputType.PHONE_NUMBER,
                errorMessage = "Please provide a valid phone number"
            )
        )
        return forms
    }

    fun getSection2FormData(): List<Form> {
        val forms = mutableListOf<Form>()
        forms.add(
            Form(
                formType = FormTypes.SINGLE_LINE_TEXT,
                question = "College Name",
                hint = "please enter your name",
                singleLineTextType = SingleLineTextType.TEXT,
                errorMessage = "Please provide an answer"
            )
        )
        forms.add(
            Form(
                formType = FormTypes.NUMBER,
                question = "Your score in college",
                hint = "example: 7.6",
                numberInputType = NumberInputType.NUMBER,
                errorMessage = "Please provide an answer"
            )
        )
        return forms
    }
```

### Step 3: 
```kotlin
   private var sectionedForms = mutableMapOf<String, List<Form>>()
   sectionedForms["Personal Information"] = getSection1FormData()
   sectionedForms["Education"] = getSection2FormData()

   binding.simpleForm.setData(sectionedForms, callback = this)
```

### Step 4: 
```kotlin
implement FormSubmitCallback in your activity and override the following function 

    override fun onFormSubmitted(forms: List<Form>) {
         //do something with the result
    }
```
<br/><br/><br/>
# 3. How to create a sectioned form and show only one section at a time

*note: user can go to next section only after filling all the mandatory fields in the current section*


|               |               |               | 
| ------------- | ------------- | ------------- |
| ![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/show_one_section1.png)  | ![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/show_one_section2.png)|![Alt Text](https://github.com/lspradeep/simple-form/blob/master/screenshots/show_one_section3.png)|

### Step 1:
   #### Create a sectioned form using the 4 steps shown in previous section

### Step 2:
   #### after creating a sectioned form, to show one section at a time to user, you can do the following
   ```kotlin
   binding.simpleForm.setData(sectionedForms, callback = this, showOnSectionAtATime = true)
   ```
   ### OR
```xml
<?xml version="1.0" encoding="utf-8"?>

    <com.pradeep.form.simple_form.SimpleFormView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:showOneSectionAtATime="true" />

```
