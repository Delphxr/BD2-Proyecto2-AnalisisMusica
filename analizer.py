


def generar_arrays():
    """[generamos los arrays con las palabras que ocupamos]

    Returns:
        [dictionary]: [disccionario con arrays de palabras positivas y negativas]
    """    

    negativo = 'negative-words.txt'

    positivo = 'positive-words.txt'


    listado = [negativo, positivo]

    diccionario = {"positivo": [], "negativo": []}


    # convertimos los archivos en arrays
    for i in range(0,len(listado)):

        archivo = open(listado[i], 'r', encoding="utf8")
        txt = archivo.read()
        words = txt.splitlines()
        listado[i] = words
        archivo.close()

    
    diccionario["negativo"] = listado[0]
    diccionario["positivo"] = listado[1]
    
    return diccionario


def evaluar(diccionario, letra):
    """[generamos una puntuacion, entre -1 y 1 sobre
        el valor emocional de la cancion, con -1 siendo depresiva, y 1 siendo positiva]

    Args:
        diccionario ([dictionary]): [diccionario con las palabras positivas y negativas]
        letra ([string]): [string con la letra de la cancion]

    Returns:
        [int]: [puntuacion que se le da a la cancion]
    """
    #quitamos todos los simbolos de puntuacion que nos pueden dar problemas
    puntuacion = ",.?!()"
    for i in puntuacion:
        letra = letra.replace(i, "")
 

    palabras = letra.split()

    palabras_usadas = {}


    #primero creamos un diccionairo que contiene las palabras y su cantidad de apariciones
    for palabra in palabras:

        palabra = palabra.lower() #pasamos a minuscula para ahorrarnos errores

        if palabra in palabras_usadas:
            palabras_usadas[palabra] += 1
        
        else:
            palabras_usadas[palabra] = 1


    #ahora hacemos el analisis y sacamos puntos
    aciertos_positivos = 0
    aciertos_negativos = 0
    aciertos_totales = 0

    for key, value in palabras_usadas.items():

        

        if key in diccionario["positivo"]:
            aciertos_positivos += value
            aciertos_totales += value
        
        if key in diccionario["negativo"]:
            aciertos_negativos += value
            aciertos_totales += value

    
    puntuacion = (aciertos_positivos-aciertos_negativos)/aciertos_totales
    print("positivos = ", aciertos_positivos, " | negativos = ", aciertos_negativos, " | total = ", aciertos_totales, " | evaluacion = ", puntuacion)
    return puntuacion


diccionario = generar_arrays()



letra_1 = "When I was a young boy My father took me into the city To see a marching band He said, 'Son, when you grow up Would you be the savior of the broken The beaten and the damned?' He said, 'Will you defeat them? Your demons, and all the non-believers The plans that they have made?' 'Because one day, I'll leave you a phantom To lead you in the summer To join the black parade' When I was a young boy My father took me into the city To see a marching band He said, 'Son, when you grow up Would you be the savior of the broken The beaten and the damned?' Sometimes I get the feelin' She's watchin' over me And other times I feel like I should go And through it all, the rise and fall The bodies in the streets And when you're gone, we want you all to know We'll carry on, we'll carry on And though you're dead and gone, believe me Your memory will carry on We'll carry on And in my heart, I can't contain it The anthem won't explain it A world that sends you reelin' From decimated dreams Your misery and hate will kill us all So paint it black and take it back Let's shout it loud and clear Defiant to the end, we hear the call To carry on, we'll carry on And though you're dead and gone, believe me Your memory will carry on We'll carry on And though you're broken and defeated Your weary widow marches On and on, we carry through the fears Oh, oh, oh Disappointed faces of your peers Oh, oh, oh Take a look at me, 'cause I could not care at all Do or die, you'll never make me Because the world will never take my heart Go and try, you'll never break me We want it all, we wanna play this part I won't explain or say I'm sorry I'm unashamed, I'm gonna show my scars Give a cheer for all the broken Listen here, because it's who we are I'm just a man, I'm not a hero Just a boy, who had to sing this song I'm just a man, I'm not a hero I don't care We'll carry on, we'll carry on And though you're dead and gone, believe me Your memory will carry on You'll carry on And though you're broken and defeated Your weary widow marches, oh Do or die, you'll never make me Because the world will never take my heart Go and try, you'll never break me We want it all, we wanna play this part (we'll carry on!) Do or die, you'll never make me Because the world will never take my heart Go and try, you'll never break me We want it all, we wanna play this part (we'll carry on!)"

letra_2 = "I got a feeling That tonight's gonna be a good night That tonight's gonna be a good night That tonight's gonna be a good, good night A feeling That tonight's gonna be a good night That tonight's gonna be a good night That tonight's gonna be a good, good night A feeling (woo-hoo) That tonight's gonna be a good night That tonight's gonna be a good night That tonight's gonna be a good, good night A feeling (woo-hoo) That tonight's gonna be a good night That tonight's gonna be a good night That tonight's gonna be a good, good night Tonight's the night, let's live it up I got my money, let's spend it up (fee-) Go out and smash it, like 'Oh my God' Jump out that sofa, let's kick it, off (fee-) I know that we'll have a ball If we get down and go out, and just lose it all I feel, stressed out, I wanna let it go Let's go way out, spaced out, and losin' all control Fill up my cup, mazel tov Look at her dancin', just take it, off (fee-) Let's paint the town, we'll shut it down Let's burn the roof, and then we'll do it again Let's do it, let's do it, let's do it, let's do it And do it, and do it, let's live it up And do it, and do it, and do it, do it, do it Let's do it, let's do it, let's do it 'Cause I gotta feeling (woo-hoo) That tonight's gonna be a good night That tonight's gonna be a good night That tonight's gonna be a good, good night A feeling (woo-hoo) That tonight's gonna be a good night That tonight's gonna be a good night That tonight's gonna be a good, good night Tonight's the night (hey), let's live it up (let's live it up) I got my money (I'm paid), let's spend it up (let's spend it up) Go out and smash it (smash it), like 'Oh my God' (like 'Oh my God!') Jump out that sofa (come on), let's kick it, off (fee-) Fill up my cup (drink), mazel tov (l'chaim) Look at her dancing (move it, move it), just take it, off (fee-) Let's paint the town (paint the town), we'll shut it down (shut it down) Let's burn the roof (ooh-woo), and then we'll do it again Let's do it, let's do it, let's do it, (Let's do it) let's do it And do it (do it), and do it, let's live it up And do it (do it), and do it (and do it) And do it, do it, do it (and do it) Let's do it (and do it), let's do it (and do it) Let's do it (hey), do it (hey), do it (hey), do it Here we come, here we go, we gotta rock Easy come, easy go, now we on top Feel the shot, body rock, rock it, don't stop Round and round, up and down, around the clock Monday, Tuesday, Wednesday and Thursday (do it) Friday, Saturday, Saturday to Sunday (do it) Get, get, get, get, get with us, you know what we say, say Party every day, p-p-p-party every day And I'm feeling (woo-hoo) That tonight's gonna be a good night That tonight's gonna be a good night That tonight's gonna be a good, good night A feeling (woo-hoo) That tonight's gonna be a good night That tonight's gonna be a good night That tonight's gonna be a good, good night (woo-hoo)"

letra_3 = "Sister, I'm not much a poet, but a criminal And you never had a chance Love it, or leave it, you can't understand A pretty face, but you do so carry on And on And on I wouldn't front the scene if you paid me I'm just the way that the doctor made me, on And on And on And on Love is the red of the rose on your coffin door What's life like, bleeding on the floor The floor The floor You'll never make me leave I wear this on my sleeve Give me a reason to believe So give me all your poison And give me all your pills And give me all your hopeless hearts And make me ill You're running after something That you'll never kill If this is what you want Then fire at will Preach all you want but who's gonna save me? I keep a gun on the book you gave me, hallelujah, lock and load Black is the kiss, the touch of the serpent sun It ain't the mark or the scar that makes you one, And one, And one, And one You'll never make me leave I wear this on my sleeve Give me a reason to believe So give me all your poison And give me all your pills And give me all your hopeless hearts And make me ill You're running after something That you'll never kill If this is what you want Then fire at will You'll never make me leave I wear this on my sleeve You want to follow something Give me a better cause to lead Just give me what I need Give me a reason to believe So give me all your poison And give me all your pills And give me all your hopeless hearts And make me ill You're running after something That you'll never kill If this is what you want Then fire at will So give me all your poison And give me all your pills And give me all your hopeless hearts And make me ill You're running after something That you'll never kill If this is what you want Then fire at will"

letra_4 = "It might seem crazy what I am 'bout to say Sunshine she's here, you can take a break I'm a hot air balloon that could go to space With the air, like I don't care, baby by the way Huh (Because I'm happy) Clap along if you feel like a room without a roof (Because I'm happy) Clap along if you feel like happiness is the truth (Because I'm happy) Clap along if you know what happiness is to you (Because I'm happy) Clap along if you feel like that's what you wanna do Here come bad news talking this and that (Yeah) Well give me all you got, don't hold back (Yeah) Well I should probably warn you I'll be just fine (Yeah) No offence to you don't waste your time Here's why Clap along if you feel like a room without a roof (Because I'm happy) Clap along if you feel like happiness is the truth (Because I'm happy) Clap along if you know what happiness is to you (Because I'm happy) Clap along if you feel like that's what you wanna do Uh, bring me down Can't nothing, bring me down My level's too high to bring me down Can't nothing, bring me down, I said Bring me down, can't nothing Bring me down My level's too high to bring me down Can't nothing, bring me down, I said Clap along if you feel like a room without a roof (Because I'm happy) Clap along if you feel like happiness is the truth (Because I'm happy) Clap along if you know what happiness is to you (Because I'm happy) Clap along if you feel like that's what you wanna do Clap along if you feel like a room without a roof (Because I'm happy) Clap along if you feel like happiness is the truth (Because I'm happy) Clap along if you know what happiness is to you (Because I'm happy) Clap along if you feel like that's what you wanna do Uh, bring me down (Happy, happy, happy, happy) Can't nothing (Happy, happy, happy, happy) Bring me down, my level's too high To bring me down (Happy, happy, happy, happy) Can't nothing (Happy, happy, happy, happy) Bring me down, I said Clap along if you feel like a room without a roof (Because I'm happy) Clap along if you feel like happiness is the truth (Because I'm happy) Clap along if you know what happiness is to you (ayy, ayy, ayy) (Because I'm happy) Clap along if you feel like that's what you wanna do Clap along if you feel like a room without a roof (Because I'm happy) Clap along if you feel like happiness is the truth (Because I'm happy) Clap along if you know what happiness is to you (hey) (Because I'm happy) Clap along if you feel like that's what you wanna do Come on"

evaluar(diccionario, letra_1)
evaluar(diccionario, letra_2)
evaluar(diccionario, letra_3)
evaluar(diccionario, letra_4)

