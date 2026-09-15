#version 150

// =============================================================================
// Farthest Portal — fragment shader (grayscale End Portal)
// File: assets/thefarthestlands/shaders/core/rendertype_farthest_portal.fsh
//
// Sampler0 = textures/environment/end_sky.png      (slow background layer)
// Sampler1 = textures/entity/end_portal.png        (scrolling star layers)
//
// Edit the knobs in "LOOK" below, then F3+T in-game to reload shaders.
// =============================================================================

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

uniform float GameTime;
uniform int EndPortalLayers;

in vec4 texProj0;

// --- LOOK -------------------------------------------------------------------
// 1.0 = fully gray like a desaturated End Portal. 0.0 = vanilla cyan/purple.
const float GRAYSCALE_AMOUNT = 1.0;

// Multiplies the final brightness. 1.0 matches vanilla intensity.
const float BRIGHTNESS = 1.0;

// Solid fill behind the scrolling star layers. Vanilla End Portal uses a
// nearly black sky texture here — raise this toward 1.0 for lighter gray.
const vec3 BACKGROUND_COLOR = vec3(0.17);

// How fast the inner layers scroll. Vanilla uses 1.5.
const float SCROLL_SPEED = 1.7;

// Rec. 709 luma weights. Raise the first number to keep more red, etc.
const vec3 LUMA_WEIGHTS = vec3(0.2126, 0.7152, 0.0722);

/** Mixes {@code color} toward its luminance by {@code GRAYSCALE_AMOUNT}. */
vec3 toGray(vec3 color) {
    float luma = dot(color, LUMA_WEIGHTS);
    return mix(color, vec3(luma), GRAYSCALE_AMOUNT);
}

// Vanilla End Portal layer tints. Each entry tints one scrolling layer.
// Grayscale is applied later, so you can still tweak these for contrast.
const vec3[] COLORS = vec3[](
    vec3(0.022087, 0.098399, 0.110818),
    vec3(0.011892, 0.095924, 0.089485),
    vec3(0.027636, 0.101689, 0.100326),
    vec3(0.046564, 0.109883, 0.114838),
    vec3(0.064901, 0.117696, 0.097189),
    vec3(0.063761, 0.086895, 0.123646),
    vec3(0.084817, 0.111994, 0.166380),
    vec3(0.097489, 0.154120, 0.091064),
    vec3(0.106152, 0.131144, 0.195191),
    vec3(0.097721, 0.110188, 0.187229),
    vec3(0.133516, 0.138278, 0.148582),
    vec3(0.070006, 0.243332, 0.235792),
    vec3(0.196766, 0.142899, 0.214696),
    vec3(0.047281, 0.315338, 0.321970),
    vec3(0.204675, 0.390010, 0.302066),
    vec3(0.080955, 0.314821, 0.661491)
);

const mat4 SCALE_TRANSLATE = mat4(
    0.5, 0.0, 0.0, 0.25,
    0.0, 0.5, 0.0, 0.25,
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
);

// Builds the UV transform for one star layer. `layer` is 1-based.
mat4 end_portal_layer(float layer) {
    mat4 translate = mat4(
        1.0, 0.0, 0.0, 17.0 / layer,
        0.0, 1.0, 0.0, (2.0 + layer / 1.5) * (GameTime * SCROLL_SPEED),
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0
    );

    mat2 rotate = mat2_rotate_z(radians((layer * layer * 4321.0 + layer * 9.0) * 2.0));

    mat2 scale = mat2((4.5 - layer / 4.0) * 2.0);

    return mat4(scale * rotate) * translate * SCALE_TRANSLATE;
}

out vec4 fragColor;

void main() {
    // Skip the vanilla dark sky (Sampler0) so the portal isn't a black void.
    vec3 color = BACKGROUND_COLOR;
    // Add each scrolling star layer, desaturated, using vanilla per-layer tints.
    for (int i = 0; i < EndPortalLayers; i++) {
        color += toGray(textureProj(Sampler1, texProj0 * end_portal_layer(float(i + 1))).rgb * COLORS[i]);
    }
    fragColor = vec4(color * BRIGHTNESS, 1.0);
}
