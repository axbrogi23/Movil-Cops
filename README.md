# Tutor Virtual - Aplicación Móvil Android 📱

**Tutor Virtual** es una aplicación móvil nativa desarrollada en Kotlin para estudiantes de ESUCAR, diseñada para facilitar el seguimiento de hitos académicos, la gestión de entregas, la consulta de información frecuente y la carga de documentación en formato PDF.

---

## 🛠️ Tecnologías y Arquitectura

El proyecto está construido bajo las mejores prácticas de desarrollo Android moderno:

* **Lenguaje:** [Kotlin](https://kotlinlang.org/)
* **Arquitectura:** MVVM (Model - View - ViewModel)
* **Interfaz de Usuario:** [Jetpack Compose](https://developer.android.com/jetpack/compose) + Material3
* **Persistencia de Datos Local:** [Room Database](https://developer.android.com/training/data-storage/room) con procesamiento de anotaciones KAPT
* **Navegación:** Navigation Compose (`NavHost`)
* **Gestión de Estado:** Lifecycle & ViewModel
* **Recursos Nativos:**
  * `NotificationManager` para alertas de plazos y recordatorios.
  * `ActivityResultContracts.GetContent` para la selección nativa de documentos PDF.

---

## 🚀 Funcionalidades Principales

1. **Dashboard de Hitos (`ListaHitosScreen`):** Muestra el listado de hitos y tareas académicas con su estado actual y fechas clave.
2. **Formulario de Registro/Edición (`FormularioHitoScreen`):** Permite ingresar o modificar hitos con validaciones de campos en tiempo real.
3. **Módulo FAQ (`FaqScreen`):** Lista interactiva de preguntas frecuentes con barra de búsqueda y secciones colapsables.
4. **Notificaciones Locales:** Alertas integradas para recordar fechas de vencimiento de entregas.
5. **Carga de Documentos:** Selección e integración nativa de archivos PDF directamente desde el almacenamiento del dispositivo.

---

## 📂 Estructura del Proyecto

```text
com.cops.tutorvirtual/
│
├── data/                  # Capa de datos (Room DB, DAOs, Entidades, Helpers)
│   ├── AppDatabase.kt
│   ├── HitoEntity.kt
│   ├── HitoDao.kt
│   └── NotificationHelper.kt
│
├── ui/                    # Capa de presentación (Jetpack Compose)
│   ├── screens/
│   │   ├── ListaHitosScreen.kt
│   │   ├── FormularioHitoScreen.kt
│   │   └── FaqScreen.kt
│   └── theme/             # Configuración visual (Colores, Tipografías, Temas)
│
├── viewmodel/             # Lógica de negocio y gestión de estado
│   └── HitoViewModel.kt
│
└── MainActivity.kt        # Punto de entrada y configuración de NavHost

---

## ⚙️ Configuración e Instalación
Requisitos Previos
 * Android Studio: Jellyfish / Koala o superior.
 * JDK: versión 11.
 * SDK Mínimo: API 24 (Android 7.0 Nougat).
 * SDK Objetivo: API 34.

## Pasos para ejecutar
1. Clonar el repositorio:

git clone [https://github.com/axbrogi23/Movil-Cops.git](https://github.com/axbrogi23/Movil-Cops.git)

2. Abrir el proyecto en Android Studio.
3. Sincronizar Gradle mediante el botón Sync Project with Gradle Files.
4. Ejecutar Build > Rebuild Project para generar la compilación de Room (kapt).
5. Ejecutar la aplicación en un emulador o dispositivo físico.

👥 Integrantes del Equipo
* Repositorio: Movil-Cops
* Desarrollo: Axel Brogi, Rodrigo Godoy
